package com.viwcy.basemodel.dto;

import com.viwcy.basemodel.constant.GoodsShelve;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class GoodsDTO {

    @NotBlank(message = "标题不能为空")
    private String title;
    private String description;
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
    private String cover;
    private List<String> pics;
    private Integer isShelve = GoodsShelve.SHELVE.getValue();
}
