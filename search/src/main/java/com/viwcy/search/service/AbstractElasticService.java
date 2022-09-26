package com.viwcy.search.service;

import com.alibaba.fastjson.JSON;
import com.viwcy.search.entity.AbstractBaseElasticBean;
import com.viwcy.search.util.SearchHelper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Slf4j
public abstract class AbstractElasticService<T extends AbstractBaseElasticBean> {

    @Resource
    private SearchHelper searchHelper;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 业务主键ID查询
     */
    public T queryById(Long id) {

        SearchRequest searchRequest = queryByIdRequest(_index(), _idField(), id);
        List<T> list = searchHelper.execute(handleClass(), searchRequest);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 业务主键ID集合查询
     */
    public List<T> queryByIds(Set<Long> ids) {

        SearchRequest searchRequest = queryByIdsRequest(_index(), _idField(), ids);
        return searchHelper.execute(handleClass(), searchRequest);
    }

    /**
     * doc的主键_id更新数据
     */
    public boolean updateById(String _id, Map<String, Object> param, WriteRequest.RefreshPolicy refreshPolicy) {

        boolean update = false;
        try {
            UpdateRequest request = new UpdateRequest(_index(), _id);
            request.setRefreshPolicy(refreshPolicy);
            request.doc(param);
            restHighLevelClient.update(request, RequestOptions.DEFAULT);
            update = true;
        } catch (IOException e) {
            log.error("update has error , id = {}, e = {}", _id, e);
        }
        return update;
    }

    /**
     * 保存或更新ES
     */
    public <T extends AbstractBaseElasticBean> boolean saveOrUpdate(AbstractBaseElasticBean<T> param, WriteRequest.RefreshPolicy refreshPolicy) {

        try {
            /**
             * IndexRequest的OpType支持四种设置方式，
             * 默认private OpType opType = OpType.INDEX //  有相同的_id数据就会进行覆盖
             */
            IndexRequest request = new IndexRequest(_index()).id(param._id()).source(JSON.toJSONString(param.t()), XContentType.JSON);
            request.setRefreshPolicy(refreshPolicy);//保持此请求打开，直到刷新使此请求的内容对搜索可见
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            log.error("saveOrUpdate has error , e = " + e);
        }
        return false;
    }

    /**
     * 批量保存或更新ES
     */
    public <T extends AbstractBaseElasticBean> boolean saveOrUpdateBatch(List<T> param, WriteRequest.RefreshPolicy refreshPolicy) {

        try {
            if (CollectionUtils.isEmpty(param)) {
                return true;
            }
            final BulkRequest bulkRequest = new BulkRequest();
            param.stream().forEach(bean -> {
                IndexRequest request = new IndexRequest(_index()).id(bean._id()).source(JSON.toJSONString(bean.t()), XContentType.JSON);
                bulkRequest.add(request);
            });
            bulkRequest.setRefreshPolicy(refreshPolicy);
            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            log.error("saveOrUpdateBatch has error , e = " + e);
        }
        return false;
    }

    protected final <T extends AbstractBaseElasticBean> void buildTime(AbstractBaseElasticBean<T> param, Long id) {

        AbstractBaseElasticBean<T> bean = queryById(id);
        Date date = new Date();
        if (Objects.isNull(bean)) {
            param.setCreateTime(date);
        } else {
            if (Objects.isNull(bean.getCreateTime())) {
                param.setCreateTime(date);
            } else {
                param.setCreateTime(bean.getCreateTime());
            }
        }
        param.setUpdateTime(date);
    }

    private final SearchRequest queryByIdRequest(String index, String field, Long id) {

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery(field, id));
        builder.query(boolQueryBuilder);
        searchRequest.source(builder);
        return searchRequest;
    }

    private final SearchRequest queryByIdsRequest(String index, String field, Set<Long> ids) {

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termsQuery(field, ids));
        builder.query(boolQueryBuilder);
        searchRequest.source(builder);
        return searchRequest;
    }

    /**
     * 获取T.class泛型类
     */
    private final Class<T> handleClass() {
        Class clazz = this.getClass();
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        return (Class<T>) types[0];
    }

    /**
     * 索引名称
     */
    protected abstract String _index();

    /**
     * 主键ID字段名称
     */
    protected abstract String _idField();
}
