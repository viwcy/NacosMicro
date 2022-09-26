package com.viwcy.search.dto;

import lombok.Builder;
import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@Builder
public class SortDTO {

    private String field;
    private SortOrder order;
}
