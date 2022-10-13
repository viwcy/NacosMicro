package com.viwcy.mall.api;

import com.viwcy.basecommon.common.BaseController;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import com.viwcy.basemodel.dto.UserPageDTO;
import com.viwcy.basemodel.entity.PageEntity;
import com.viwcy.mall.feign.CustomHandle;
import com.viwcy.mall.forest.UserRemote;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@RestController
@RequestMapping("/custom")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomApi extends BaseController {

    private final CustomHandle customHandle;

    @Resource
    private UserRemote userRemote;

    @PostMapping("/user/page")
    public ResultEntity<PageEntity<List<SimpleUserDTO>>> page(@RequestBody UserPageDTO dto, HttpServletRequest request) {
//        return customHandle.page(dto);
        String jwt = request.getHeader("Authorization");
        return userRemote.page(dto,jwt);
    }
}
