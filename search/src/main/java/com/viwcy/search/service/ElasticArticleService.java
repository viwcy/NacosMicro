package com.viwcy.search.service;

import com.viwcy.search.constant.SearchConstant;
import com.viwcy.search.factory.SearchFactory;
import com.viwcy.search.handle.ArticleSearchHandle;
import com.viwcy.search.handle.base.BaseSearch;
import com.viwcy.search.param.ElasticArticleSearchReq;
import com.viwcy.search.param.base.PageReq;
import com.viwcy.search.vo.PageVO;
import com.viwcy.search.entity.ElasticArticle;
import com.viwcy.search.repository.ElasticArticleRepository;
import org.elasticsearch.action.support.WriteRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Service
public class ElasticArticleService extends AbstractElasticService<ElasticArticle> {

    @Resource
    private ElasticArticleRepository articleRepository;
    @Resource
    private ArticleSearchHandle articleSearchHandle;
    @Resource
    private SearchFactory searchFactory;

    public final void save(ElasticArticle param) {

        buildTime(param, param.getId());
        super.saveOrUpdate(param, WriteRequest.RefreshPolicy.WAIT_UNTIL);
    }

    public final void saveBatch(List<ElasticArticle> param) {

        param.stream().forEach(bean -> buildTime(bean, bean.getId()));
        super.saveOrUpdateBatch(param, WriteRequest.RefreshPolicy.WAIT_UNTIL);
    }

    public final ElasticArticle query(Long _id) {

        return articleRepository.query(_id);
    }

    public final List<ElasticArticle> queryBatch(Set<Long> _ids) {

        return articleRepository.queryBatch(_ids);
    }

    public final PageVO<ElasticArticle> generalSearch(ElasticArticleSearchReq req) {

        PageReq pageReq = new PageReq(req.getPage(), req.getSize());
        return articleSearchHandle.generalPage(ElasticArticle.class, req, pageReq);
    }

    public final List<ElasticArticle> list(ElasticArticleSearchReq req) {

        BaseSearch handler = searchFactory.getHandler(SearchConstant.ARTICLE_SEARCH_HANDLER);
        return handler.list(ElasticArticle.class, req);
    }

    @Override
    protected String _index() {
        return SearchConstant.ARTICLE_INDEX;
    }

    @Override
    protected String _idField() {
        return SearchConstant.ARTICLE_ID_FIELD;
    }
}
