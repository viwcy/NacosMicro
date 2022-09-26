package com.viwcy.search.api;

import com.viwcy.basecommon.common.BaseController;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.search.entity.ElasticBook;
import com.viwcy.search.param.ElasticBookSearchReq;
import com.viwcy.search.service.ElasticBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@RestController
@RequestMapping("/book")
public class BookSearchApi extends BaseController {

    @Resource
    private ElasticBookService elasticBookService;

    @PostMapping("/save")
    public ResultEntity save(@RequestBody ElasticBook param) {

        elasticBookService.save(param);
        return success();
    }

    @PostMapping("/saveBatch")
    public ResultEntity saveBatch(@RequestBody List<ElasticBook> param) {

        elasticBookService.saveBatch(param);
        return success();
    }

    @PostMapping("/query")
    public ResultEntity query(@RequestParam Long _id) {

        return success(elasticBookService.query(_id));
    }

    @PostMapping("/queryBatch")
    public ResultEntity queryBatch(@RequestParam Set<Long> _ids) {

        return success(elasticBookService.queryBatch(_ids));
    }

    @PostMapping("/queryById")
    public ResultEntity queryById(@RequestParam Long id) {

        return success(elasticBookService.queryById(id));
    }

    @PostMapping("/queryByIds")
    public ResultEntity queryByIds(@RequestParam Set<Long> ids) {

        return success(elasticBookService.queryByIds(ids));
    }

    @PostMapping("/update")
    public ResultEntity update(@RequestBody Map<String, Object> param) {

        return success(elasticBookService.update(param));
    }

    @PostMapping("/keywordSearch")
    public ResultEntity keywordSearch(@RequestBody ElasticBookSearchReq req) {

        return success(elasticBookService.generalSearch(req));
    }

    @PostMapping("/search")
    public ResultEntity search(@RequestBody ElasticBookSearchReq req) {

        return success(elasticBookService.search(req));
    }

    @PostMapping("/insert")
    public ResultEntity insert(@RequestBody ElasticBook param) {

        return success(elasticBookService.insert(param));
    }

    @PostMapping("/insertBatch")
    public ResultEntity insertBatch(@RequestBody List<ElasticBook> param) {

        return success(elasticBookService.insertBatch(param));
    }
}
