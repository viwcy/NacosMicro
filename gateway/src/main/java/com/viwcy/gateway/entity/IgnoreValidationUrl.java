package com.viwcy.gateway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.viwcy.gateway.action.DeleteAction;
import com.viwcy.gateway.action.SaveAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ignore_validation_url")
@Builder
public class IgnoreValidationUrl extends Model<IgnoreValidationUrl> implements Serializable {

    @TableId(type = IdType.AUTO)
    @NotNull(groups = {DeleteAction.class}, message = "主键ID不能为空")
    private Long id;
    @NotBlank(groups = {SaveAction.class}, message = "url不能为空")
    private String ignoreUrl;
    @NotBlank(groups = {SaveAction.class}, message = "remark不能为空")
    private String remark;
}
