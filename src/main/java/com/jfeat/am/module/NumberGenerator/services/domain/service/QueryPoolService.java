package com.jfeat.am.module.NumberGenerator.services.domain.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface QueryPoolService {
    List<Pool> findPoolPage(Page<Pool> page, Pool pool );
}