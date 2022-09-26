package com.viwcy.search.handle.base;

import com.viwcy.basecommon.exception.BaseException;
import com.viwcy.basecommon.exception.BusinessException;
import com.viwcy.search.dto.SortDTO;
import com.viwcy.search.dto.TimeDTO;
import com.viwcy.search.param.base.PageReq;
import com.viwcy.search.util.SearchHelper;
import com.viwcy.search.param.base.BaseSearchReq;
import com.viwcy.search.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Slf4j
public abstract class AbstractBaseSearch implements BaseSearch {

    @Autowired
    private SearchHelper searchHelper;

    @Override
    public <T> PageVO<T> page(Class<T> clazz, BaseSearchReq req) {

        PageVO<T> vo = new PageVO<>();
        PageReq page = new PageReq(req.getPage(), req.getSize());
        SearchResponse response = response(req, page);
        if (Objects.isNull(response)) {
            return vo;
        }
        List<T> list = searchHelper.handleResponse(clazz, response, req.getHighLightFields());
        vo.setResult(list);
        vo.setTotal(response.getHits().getTotalHits().value);
        return vo;
    }

    @Override
    public <T> List<T> list(Class<T> clazz, BaseSearchReq req) {

        SearchResponse response = response(req, null);
        if (Objects.isNull(response)) {
            return Collections.emptyList();
        }
        return searchHelper.handleResponse(clazz, response, req.getHighLightFields());
    }

    protected final void preCheck(BaseSearchReq req) {

        List<String> fields = req.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            throw new BusinessException("match fields can not be empty");
        }
        String keyword = req.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
            throw new BusinessException("keyword can not be empty");
        }
    }

    private final SearchResponse response(BaseSearchReq req, PageReq page) {

        String[] indices = req.getIndices();
        if (indices.length == 0) {
            throw new BaseException("indices can not be empty");
        }
        //文档集合
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder builder = buildQuery(req);
        if (!Objects.isNull(page)) {
            buildPage(builder, page.getPage(), page.getSize());
        }
        searchRequest.source(builder);
        return searchHelper.searchResponse(searchRequest);
    }

    protected final void buildPage(SearchSourceBuilder builder, int page, int size) {

        builder.from((page - 1) * size);//page换算from
        builder.size(size);
        builder.trackTotalHits(true);
    }

    /**
     * 构建通用builder，多字段查询keyword，限制时间，排序等
     */
    public final <T> PageVO<T> generalPage(Class<T> clazz, BaseSearchReq req, PageReq page) {

        List<String> fields = req.getFields();
        String keyword = req.getKeyword();
        preCheck(req);

        //总节点
        BoolQueryBuilder root = QueryBuilders.boolQuery();

        BoolQueryBuilder rootKeywordBuilder = QueryBuilders.boolQuery();
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            rootKeywordBuilder.should(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery(field, keyword).boost(getBoost(field))));
        }
        root.must(rootKeywordBuilder);

        SearchSourceBuilder builder = new SearchSourceBuilder();

        //时间
        BoolQueryBuilder rangeBuilder = QueryBuilders.boolQuery();
        Collection<TimeDTO> ranges = req.getRanges();
        if (!CollectionUtils.isEmpty(ranges)) {
            ranges.stream().forEach(range -> {
                rangeBuilder.must(QueryBuilders.rangeQuery(range.getField()).gte(range.getStart()).lte(range.getEnd()));
            });
            root.must(rangeBuilder);
        }

        //排序
        Collection<SortDTO> sorts = req.getOrders();
        if (!CollectionUtils.isEmpty(sorts)) {
            for (SortDTO sort : sorts) {
                builder.sort(sort.getField(), sort.getOrder());
            }
        }

        //分页
        buildPage(builder, page.getPage(), page.getSize());

        //高亮
        searchHelper.setHighLight(req.getHighLightFields(), builder);

        builder.query(root);
        SearchRequest searchRequest = new SearchRequest(req.getIndices());
        searchRequest.source(builder);
        SearchResponse response = searchHelper.searchResponse(searchRequest);
        PageVO<T> vo = new PageVO<>();
        List<T> list = searchHelper.handleResponse(clazz, response, req.getHighLightFields());
        vo.setResult(list);
        vo.setTotal(response.getHits().getTotalHits().value);
        return vo;
    }

    /**
     * 构建查询条件，各自的复杂业务
     */
    protected abstract SearchSourceBuilder buildQuery(BaseSearchReq req);

    /**
     * 设置字段权重
     */
    protected abstract float getBoost(String field);
}
