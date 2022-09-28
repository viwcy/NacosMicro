package com.viwcy.search.handle.base;

import com.viwcy.basecommon.exception.BaseException;
import com.viwcy.basecommon.exception.BusinessException;
import com.viwcy.search.param.base.PageReq;
import com.viwcy.search.util.SearchHelper;
import com.viwcy.search.param.base.BaseSearchReq;
import com.viwcy.search.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Slf4j
public abstract class AbstractBaseSearch<T> implements BaseSearch<T> {

    @Autowired
    private SearchHelper searchHelper;

    @Override
    public PageVO<T> page(BaseSearchReq req) {

        PageVO<T> vo = new PageVO<>();
        PageReq page = new PageReq(req.getPage(), req.getSize());
        SearchResponse response = response(req, page);
        if (Objects.isNull(response)) {
            return vo;
        }
        List<T> list = searchHelper.handleResponse(handleClass(), response, req.getHighLightFields());
        vo.setResult(list);
        vo.setTotal(response.getHits().getTotalHits().value);
        return vo;
    }

    @Override
    public List<T> list(BaseSearchReq req) {

        SearchResponse response = response(req, null);
        if (Objects.isNull(response)) {
            return Collections.emptyList();
        }
        return searchHelper.handleResponse(handleClass(), response, req.getHighLightFields());
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
     * 获取T.class泛型类
     */
    private final Class<T> handleClass() {
        Class clazz = this.getClass();
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        return (Class<T>) types[0];
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
