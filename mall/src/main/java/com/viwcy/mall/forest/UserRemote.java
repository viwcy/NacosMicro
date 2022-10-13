package com.viwcy.mall.forest;

import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import com.viwcy.basemodel.dto.UserPageDTO;
import com.viwcy.basemodel.entity.PageEntity;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
public interface UserRemote {

    /**
     * 集成forest远程调用
     *
     * @see <a href="https://forest.dtflyx.com/">forest/</a>
     */
    @Post(url = "http://127.0.0.1:9001/user/page")
    ResultEntity<PageEntity<List<SimpleUserDTO>>> page(@JSONBody UserPageDTO dto, @Header("Authorization") String jwt);
}
