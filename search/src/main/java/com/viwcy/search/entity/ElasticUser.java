package com.viwcy.search.entity;

import com.viwcy.search.constant.SearchConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = SearchConstant.USER_INDEX)
@Setting(settingPath = "user/user_setting.json")
@Mapping(mappingPath = "user/user_mapping.json")
public class ElasticUser extends AbstractBaseElasticBean<ElasticUser> implements Serializable {

    private static final long serialVersionUID = -4037331741638849111L;

    private Long id;
    private String userName;
    /**
     * phone和email内容较短，可采用wildcard进行模糊检索
     * 可以使用自定义的char_analyzer进行检索
     */
    private String phone;
    private String email;

    @Override
    public String _id() {
        return String.valueOf(this.id);
    }
}
