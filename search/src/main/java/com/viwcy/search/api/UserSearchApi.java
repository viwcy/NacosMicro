package com.viwcy.search.api;

import com.viwcy.basecommon.common.BaseController;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.search.param.ElasticUserSearchReq;
import com.viwcy.search.entity.ElasticUser;
import com.viwcy.search.service.ElasticUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@RestController
@RequestMapping("/user")
public class UserSearchApi extends BaseController {

    @Resource
    private ElasticUserService elasticUserService;

    @PostMapping("/query")
    public ResultEntity query(@RequestParam Long _id) {

        return success(elasticUserService.query(_id));
    }

    @PostMapping("/queryBatch")
    public ResultEntity queryBatch(@RequestParam Set<Long> _ids) {

        return success(elasticUserService.queryBatch(_ids));
    }

    @PostMapping("/queryById")
    public ResultEntity queryById(@RequestParam Long id) {

        return success(elasticUserService.queryById(id));
    }

    @PostMapping("/queryByIds")
    public ResultEntity queryByIds(@RequestParam Set<Long> ids) {

        return success(elasticUserService.queryByIds(ids));
    }

    @PostMapping("/keywordSearch")
    public ResultEntity keywordSearch(@RequestBody ElasticUserSearchReq req) {

        return success(elasticUserService.generalSearch(req));
    }

    @PostMapping("/save")
    public ResultEntity save(@RequestBody ElasticUser param) {

        elasticUserService.save(param);
        return success();
    }

    @PostMapping("/saveBatch")
    public ResultEntity saveBatch(@RequestBody List<ElasticUser> param) {

        elasticUserService.saveBatch(param);
        return success();
    }
}
