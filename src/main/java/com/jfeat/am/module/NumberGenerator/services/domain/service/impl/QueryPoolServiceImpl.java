package com.jfeat.am.module.NumberGenerator.services.domain.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.NumberGenerator.services.domain.dao.QueryPoolDao;
import com.jfeat.am.module.NumberGenerator.services.domain.service.QueryPoolService;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service
public class QueryPoolServiceImpl implements QueryPoolService {

    @Resource
    QueryPoolDao queryPoolDao;

    @Override
    public List<Pool> findPoolPage(Page<Pool> page, Pool pool) {
        return queryPoolDao.findPoolPage(page, pool);
    }
}
