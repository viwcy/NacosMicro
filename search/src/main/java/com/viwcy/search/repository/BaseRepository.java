package com.viwcy.search.repository;

import com.viwcy.basecommon.exception.BaseException;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends ElasticsearchRepository<T, ID> {

    /**
     * 主键ID查找
     * 即doc的_id值，并不是业务实体的id
     */
    default T query(ID _id) {

        if (StringUtils.isEmpty(_id)) {
            throw new BaseException("_id has not be null");
        }
        T t = null;
        Optional<T> optional = this.findById(_id);
        if (optional.isPresent()) {
            t = optional.get();
        }
        return t;
    }

    /**
     * 主键ID集合查询
     */
    default List<T> queryBatch(Collection<ID> _ids) {

        if (CollectionUtils.isEmpty(_ids)) {
            throw new BaseException("Collection _ids has not be empty");
        }
        return (List<T>) this.findAllById(_ids);
    }

    /**
     * 批量删除，传参ID集合
     */
    default boolean deleteBatch(Collection<ID> _ids) {

        if (CollectionUtils.isEmpty(_ids)) {
            throw new BaseException("collection _ids has not be empty");
        }
        _ids.stream().forEach(_id->this.deleteById(_id));
        return true;
    }

    /**
     * 覆盖保存
     */
    default <S extends T> S insert(S entity) {

        return save(entity);
    }

    /**
     * 批量覆盖保存
     */
    default <S extends T> List<S> insertBatch(List<S> entities) {

        return (List<S>) saveAll(entities);
    }
}
