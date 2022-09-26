package com.viwcy.custom.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.viwcy.basecommon.annotation.IgnoreToken;
import com.viwcy.basecommon.common.BaseController;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import com.viwcy.basemodel.dto.UserLoginDTO;
import com.viwcy.basemodel.dto.UserPageDTO;
import com.viwcy.basemodel.entity.PageEntity;
import com.viwcy.custom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserApi extends BaseController {

    private final UserService userService;

    @PostMapping("/register")
    @IgnoreToken
    public ResultEntity save(@RequestBody User user) {
        return userService.register(user) ? success("恭喜您，账号注册成功") : fail("很遗憾，账号注册失败");
    }

    @PostMapping("/login")
    @IgnoreToken
    public ResultEntity login(@RequestBody @Validated UserLoginDTO dto, HttpServletRequest request) {
        JSONObject login = userService.login(dto);
        request.setAttribute("user", login);
        return success(login);
    }

    @GetMapping("refresh")
    public ResultEntity refresh() {

        return success(userService.refresh());
    }

    @PostMapping("/page")
    public ResultEntity<PageEntity<List<SimpleUserDTO>>> page(@RequestBody UserPageDTO dto) {
        return success(userService.queryPage(dto));
    }
}
