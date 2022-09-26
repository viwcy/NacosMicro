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
@Document(indexName = SearchConstant.ARTICLE_INDEX)
@Setting(settingPath = "article/article_setting.json")
@Mapping(mappingPath = "article/article_mapping.json")
public class ElasticArticle extends AbstractBaseElasticBean<ElasticArticle> implements Serializable {

    private static final long serialVersionUID = -5475106814753685798L;

    private Long id;
    private String author;
    private String content;

    @Override
    public String _id() {
        return String.valueOf(this.id);
    }
}
