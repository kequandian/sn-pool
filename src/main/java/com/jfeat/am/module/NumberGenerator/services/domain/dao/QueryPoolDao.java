package com.jfeat.am.module.NumberGenerator.services.domain.dao;

import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by Code Generator on 2017-12-05
 */
public interface QueryPoolDao extends BaseMapper<Pool> {
    List<Pool> findPoolPage(Page<Pool> page,@Param("pool") Pool pool);
}