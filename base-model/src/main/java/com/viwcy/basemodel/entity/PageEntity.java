package com.viwcy.basemodel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageEntity<T> implements Serializable {
    private static final long serialVersionUID = -7518675912627630317L;

    private T records;
    private long total;

    public static <T> PageEntity<T> of(T records, long total) {
        return new PageEntity(records, total);
    }

    public static <T> PageEntity<T> of() {
        return new PageEntity(new ArrayList<>(), 0);
    }
}
