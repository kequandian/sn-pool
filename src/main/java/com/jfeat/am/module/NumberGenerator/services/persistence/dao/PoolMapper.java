package com.jfeat.am.module.NumberGenerator.services.persistence.dao;

import com.jfeat.am.module.NumberGenerator.config.PageForPool;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

        public Integer maxUsed(@Param("num")Integer num);

        public void clearAll(List<String> list);

        public void setUsed(Pool pool);

        public void reback(Pool pool);

        public void initTable();

        public List<String> showTables();

        public void addPrefix(@Param("prefix") String prefix);

        public List<Pool> preOrSuf(PageForPool pageForPool);

        public List<String> showField();

        public void addConfig(@Param("time") String time);

        public void setConfig(@Param("time") String time);

        public Integer preOrSufCount(@Param("preOrSuf") String preOrSuf);
}