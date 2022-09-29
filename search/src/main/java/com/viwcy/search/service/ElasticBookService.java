package com.viwcy.search.service;

import com.viwcy.search.constant.SearchConstant;
import com.viwcy.search.entity.ElasticBook;
import com.viwcy.search.factory.SearchFactory;
import com.viwcy.search.handle.base.BaseSearch;
import com.viwcy.search.param.ElasticBookSearchReq;
import com.viwcy.search.repository.ElasticBookRepository;
import com.viwcy.search.vo.PageVO;
import org.elasticsearch.action.support.WriteRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Service
public class ElasticBookService extends AbstractElasticService<ElasticBook> {

    @Resource
    private ElasticBookRepository elasticBookRepository;
    @Resource
    private SearchFactory<ElasticBook> searchFactory;

    public final void save(ElasticBook param) {

        buildTime(param, param.getId());
        super.saveOrUpdate(param, WriteRequest.RefreshPolicy.WAIT_UNTIL);
    }

    public final void saveBatch(List<ElasticBook> param) {

        param.stream().forEach(bean -> buildTime(bean, bean.getId()));
        super.saveOrUpdateBatch(param, WriteRequest.RefreshPolicy.WAIT_UNTIL);
    }

    public final ElasticBook query(Long _id) {

        return elasticBookRepository.query(_id);
    }

    public final List<ElasticBook> queryBatch(Set<Long> _ids) {

        return elasticBookRepository.queryBatch(_ids);
    }

    public final boolean update(ElasticBook param) {

        return super.updateById(param, WriteRequest.RefreshPolicy.WAIT_UNTIL);
    }

    public final ElasticBook insert(ElasticBook param) {

        buildTime(param, param.getId());
        return elasticBookRepository.insert(param);
    }

    public final List<ElasticBook> insertBatch(List<ElasticBook> param) {

        param.stream().forEach(bean -> buildTime(bean, bean.getId()));
        return elasticBookRepository.insertBatch(param);
    }

    public final PageVO<ElasticBook> search(ElasticBookSearchReq req) {

        BaseSearch<ElasticBook> handler = searchFactory.getHandler(SearchConstant.SearchHandler.BOOK_SEARCH_HANDLER);
        return handler.page(req);
    }

    @Override
    protected String index() {
        return SearchConstant.BookIndex.INDEX;
    }

    @Override
    protected String idField() {
        return SearchConstant.BookIndex.ID_FIELD;
    }
}
