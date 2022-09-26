package com.viwcy.search.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class PageVO<T> implements Serializable {

    private static final long serialVersionUID = -1538969892586525792L;

    private List<T> result = Collections.synchronizedList(new ArrayList<>());

    private long total;
}
