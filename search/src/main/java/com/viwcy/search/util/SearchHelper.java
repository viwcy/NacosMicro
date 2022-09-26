package com.viwcy.search.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.viwcy.basecommon.exception.BaseException;
import com.viwcy.search.constant.SearchConstant;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Slf4j
@Component
public class SearchHelper {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 执行查询，并且封装返回结果
     */
    public final <T> List<T> execute(Class<T> clazz, SearchRequest searchRequest) {

        SearchResponse response = searchResponse(searchRequest);
        if (Objects.isNull(response)) {
            return new ArrayList<>();
        }
        return handleResponse(clazz, response, Lists.newArrayList());
    }

    /**
     * 执行查询，并且封装返回结果
     */
    public final <T> List<T> execute(Class<T> clazz, SearchRequest searchRequest, List<String> highLightFields) {

        SearchResponse response = searchResponse(searchRequest);
        if (Objects.isNull(response)) {
            return new ArrayList<>();
        }
        return handleResponse(clazz, response, highLightFields);
    }

    /**
     * 执行查询，获取SearchResponse
     */
    public final SearchResponse searchResponse(SearchRequest searchRequest) {

        SearchResponse response = null;
        if (Objects.isNull(searchRequest)) {
            log.error("SearchHelper#execute 'searchRequest' is null");
            return null;
        }
        log.info("DSL query : " + searchRequest.source());
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("RestHighLevelClient search has error , e = " + e);
        }
        log.debug("SearchHelper#execute , SearchResponse = " + response);
        return response;
    }

    /**
     * 设置高亮
     */
    public final void setHighLight(List<String> highLightFields, @NonNull SearchSourceBuilder builder) {

        if (CollectionUtils.isEmpty(highLightFields)) {
            return;
        }
        HighlightBuilder highlight = new HighlightBuilder();
        for (String highLightField : highLightFields) {
            highlight.field(highLightField);
        }
        highlight.requireFieldMatch(false);
        highlight.preTags(SearchConstant.PRE_TAG);
        highlight.postTags(SearchConstant.POST_TAG);
        //⽂字内容等有很多字的字段,必须配置,不然会导致⾼亮不全,⽂章内容缺失等
        highlight.fragmentSize(200); //最⼤⾼亮分⽚数
        highlight.numOfFragments(5); //从第⼀个分⽚获取⾼亮⽚段
        highlight.highlighterType("unified");
        builder.highlighter(highlight);
    }

    /**
     * 替换高亮字段
     */
    public final <T> void replaceHighLightField(@NonNull T t, @NonNull SearchHit hit, List<String> highLightFields) {

        if (CollectionUtils.isEmpty(highLightFields)) {
            throw new BaseException("high light fields not be empty");
        }
        Map<String, HighlightField> highMap = hit.getHighlightFields();
        for (String highLightField : highLightFields) {
            HighlightField highlightField = highMap.get(highLightField);
            if (highlightField != null) {
                Text[] fragments = highlightField.getFragments();
                String highStr = "";
                for (Text fragment : fragments) {
                    highStr += fragment;
                }
                Field declaredField;
                try {
                    declaredField = t.getClass().getDeclaredField(highLightField);
                    declaredField.setAccessible(true);
                    declaredField.set(t, highStr);
                } catch (Exception e) {
                    log.error("set high light fields has error , e = " + e);
                }
            }
        }
    }

    /**
     * 设置分值
     */
    public final <T> void setScore(@NonNull T t, SearchHit hit) {

        if (Objects.isNull(hit)) {
            throw new BaseException("SearchHit can not be null");
        }
        try {
            Field score = t.getClass().getSuperclass().getDeclaredField("score");
            if (!Objects.isNull(score)) {
                score.setAccessible(true);
                score.set(t, hit.getScore());
            } else {
                log.info("To obtain field 'score' is null");
            }
        } catch (Exception e) {
            log.error("set score has error , e = " + e);
        }
    }

    /**
     * 处理查询响应结果，返回数据集合
     */
    public final <T> List<T> handleResponse(Class<T> clazz, SearchResponse response, List<String> highLightFields) {

        List<T> list = new ArrayList<>();
        if (Objects.isNull(response)) {
            return list;
        }
        SearchHits searchHits = response.getHits();
        if (Objects.isNull(searchHits)) {
            return list;
        }
        SearchHit[] hits = searchHits.getHits();
        if (hits.length == 0) {
            return list;
        }
        for (SearchHit hit : hits) {
            T t = JSON.parseObject(hit.getSourceAsString(), clazz);
            //设置score分值
            setScore(t, hit);
            //替换高亮字段
            if (!CollectionUtils.isEmpty(highLightFields)) {
                replaceHighLightField(t, hit, highLightFields);
            }
            list.add(t);
        }
        return list;
    }
}
