package com.viwcy.search.service;

import com.viwcy.search.constant.SearchConstant;
import com.viwcy.search.entity.ElasticUser;
import com.viwcy.search.repository.ElasticUserRepository;
import org.elasticsearch.action.support.WriteRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Service
public class ElasticUserService extends AbstractElasticService<ElasticUser> {

    @Resource
    private ElasticUserRepository userRepository;

    public final void save(ElasticUser param) {

        buildTime(param, param.getId());
        super.saveOrUpdate(param, WriteRequest.RefreshPolicy.WAIT_UNTIL);
    }

    public final void saveBatch(List<ElasticUser> param) {

        param.stream().forEach(bean -> buildTime(bean, bean.getId()));
        super.saveOrUpdateBatch(param, WriteRequest.RefreshPolicy.WAIT_UNTIL);
    }

    public final ElasticUser query(Long _id) {

        return userRepository.query(_id);
    }

    public final List<ElasticUser> queryBatch(Set<Long> _ids) {

        return userRepository.queryBatch(_ids);
    }

    @Override
    protected String _index() {
        return SearchConstant.UserIndex._INDEX;
    }

    @Override
    protected String _idField() {
        return SearchConstant.UserIndex._ID_FIELD;
    }
}
