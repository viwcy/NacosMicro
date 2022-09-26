package com.viwcy.search.api;

import com.viwcy.basecommon.common.BaseController;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.search.param.ElasticArticleSearchReq;
import com.viwcy.search.entity.ElasticArticle;
import com.viwcy.search.service.ElasticArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@RestController
@RequestMapping("/article")
public class ArticleSearchApi extends BaseController {

    @Resource
    private ElasticArticleService elasticArticleService;

    @PostMapping("/save")
    public ResultEntity save(@RequestBody ElasticArticle param) {

        elasticArticleService.save(param);
        return success();
    }

    @PostMapping("/saveBatch")
    public ResultEntity saveBatch(@RequestBody List<ElasticArticle> param) {

        elasticArticleService.saveBatch(param);
        return success();
    }

    @PostMapping("/query")
    public ResultEntity query(@RequestParam Long _id) {

        return success(elasticArticleService.query(_id));
    }

    @PostMapping("/queryBatch")
    public ResultEntity queryBatch(@RequestParam Set<Long> _ids) {

        return success(elasticArticleService.queryBatch(_ids));
    }

    @PostMapping("/queryById")
    public ResultEntity queryById(@RequestParam Long id) {

        return success(elasticArticleService.queryById(id));
    }

    @PostMapping("/queryByIds")
    public ResultEntity queryByIds(@RequestParam Set<Long> ids) {

        return success(elasticArticleService.queryByIds(ids));
    }

    @PostMapping("/keywordSearch")
    public ResultEntity keywordSearch(@RequestBody ElasticArticleSearchReq req) {

        return success(elasticArticleService.generalSearch(req));
    }

    @PostMapping("/page")
    public ResultEntity page(@RequestBody ElasticArticleSearchReq req) {

        return success(elasticArticleService.list(req));
    }
}
