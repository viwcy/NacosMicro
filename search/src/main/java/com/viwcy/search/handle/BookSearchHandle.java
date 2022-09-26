package com.viwcy.search.handle;

import com.viwcy.search.constant.SearchConstant;
import com.viwcy.search.dto.TimeDTO;
import com.viwcy.search.handle.base.AbstractBaseSearch;
import com.viwcy.search.param.base.BaseSearchReq;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.Collection;


/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Service(SearchConstant.BOOK_SEARCH_HANDLER)
public class BookSearchHandle extends AbstractBaseSearch {

    @Override
    protected SearchSourceBuilder buildQuery(BaseSearchReq req) {

        //TODO  当前业务复杂的构建builder
        preCheck(req);
        BoolQueryBuilder root = QueryBuilders.boolQuery();
        root.must(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchPhraseQuery("author", req.getKeyword()))
                        .should(QueryBuilders.matchPhraseQuery("bookName", req.getKeyword()))
        );

        Collection<TimeDTO> ranges = req.getRanges();
        ranges.stream().forEach(range -> {
            root.must(QueryBuilders.rangeQuery(range.getField()).gte(range.getStart()).lte(range.getEnd()));
        });

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
            case "bookName":
                boost = 3.0f;
                break;
            default:
                boost = 1.0f;
        }
        return boost;
    }
}
