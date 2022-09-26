package com.viwcy.search.param.base;

import com.google.common.collect.Lists;
import com.viwcy.search.dto.SortDTO;
import com.viwcy.search.dto.TimeDTO;
import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 * 顶层请求实体
 */
@Data
public abstract class BaseSearchReq extends PageReq {

    //文档集合
    public abstract String[] getIndices();

    private String keyword;

    /**
     * 实体多字段匹配集合
     */
    private List<String> fields;

    /**
     * 排序字段：默认分值倒序
     */
    private List<SortDTO> orders = Lists.newArrayList(SortDTO.builder().field("_score").order(SortOrder.DESC).build());

    /**
     * 高亮字段
     */
    private List<String> highLightFields = Lists.newArrayList();

    /**
     * 时间范围
     */
    private List<TimeDTO> ranges = Lists.newArrayList();
}
