package com.viwcy.search.entity;

import com.viwcy.search.constant.SearchConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = SearchConstant.BookIndex._INDEX)
@Setting(settingPath = "book/book_setting.json")
@Mapping(mappingPath = "book/book_mapping.json")
public class ElasticBook extends AbstractBaseElasticBean<ElasticBook> implements Serializable {

    private static final long serialVersionUID = 3145429283527211830L;

    private Long id;
    private String author;
    private String bookName;

    @Override
    public String _id() {
        return String.valueOf(this.id);
    }
}
