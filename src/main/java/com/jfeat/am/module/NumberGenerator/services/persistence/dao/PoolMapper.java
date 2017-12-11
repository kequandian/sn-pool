package com.jfeat.am.module.NumberGenerator.services.persistence.dao;

import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 订单池 Mapper 接口
 * </p>
 *
 * @author Code Generator
 * @since 2017-12-05
 */
public interface PoolMapper extends BaseMapper<Pool> {
        public void batchInsert(List<Pool> pools);

        public Integer maxUsed();

        public void clearAll();

        public void setUsed(Pool pool);
}