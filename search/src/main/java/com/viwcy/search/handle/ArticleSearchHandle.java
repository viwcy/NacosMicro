package com.viwcy.search.handle;

import com.viwcy.search.constant.SearchConstant;
import com.viwcy.search.entity.ElasticArticle;
import com.viwcy.search.handle.base.AbstractBaseSearch;
import com.viwcy.search.param.base.BaseSearchReq;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Service(SearchConstant.SearchHandler.ARTICLE_SEARCH_HANDLER)
public class ArticleSearchHandle extends AbstractBaseSearch<ElasticArticle> {

    @Override
    protected SearchSourceBuilder buildQuery(BaseSearchReq req) {

        List<String> fields = req.getFields();
        String keyword = req.getKeyword();
        preCheck(req);
        //TODO  当前业务复杂的构建builder
        //总节点
        BoolQueryBuilder root = QueryBuilders.boolQuery();

        BoolQueryBuilder rootKeywordBuilder = QueryBuilders.boolQuery();
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            rootKeywordBuilder.should(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery(field, keyword).boost(getBoost(field))));
        }
        root.must(rootKeywordBuilder);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(root);
        return builder;
    }

    @Override
    protected float getBoost(String field) {

        float boost;
        switch (field) {
            case "author":
                boost = 5.0f;
                break;
            case "content":
                boost = 3.0f;
                break;
            default:
                boost = 1.0f;
        }
        return boost;
    }
}
