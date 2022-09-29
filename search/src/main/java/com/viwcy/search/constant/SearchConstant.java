package com.viwcy.search.constant;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public class SearchConstant {

    /**
     * 高亮标签
     */
    public static final String PRE_TAG = "<span style='color: red'>";
    public static final String POST_TAG = "</span>";

    /**
     * handler
     */
    public final class SearchHandler {
        public static final String USER_SEARCH_HANDLER = "userSearchHandle";
        public static final String ARTICLE_SEARCH_HANDLER = "articleSearchHandle";
        public static final String BOOK_SEARCH_HANDLER = "bookSearchHandle";
    }

    /**
     * index user
     */
    public final class UserIndex {
        public static final String INDEX = "user";
        public static final String ID_FIELD = "id";
    }


    /**
     * index article
     */
    public final class ArticleIndex {
        public static final String INDEX = "article";
        public static final String ID_FIELD = "id";
    }

    /**
     * index book
     */
    public final class BookIndex {
        public static final String INDEX = "book";
        public static final String ID_FIELD = "id";
    }
}
