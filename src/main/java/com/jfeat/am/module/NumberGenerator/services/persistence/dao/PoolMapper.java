package com.jfeat.am.module.NumberGenerator.services.persistence.dao;

import com.jfeat.am.module.NumberGenerator.config.PageForPool;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface PoolMapper extends BaseMapper<Pool> {
     void batchInsert(List<Pool> pools);

    Integer maxUsed(@Param("num") Integer num);

     void clearAll(List<String> list);

     void setUsed(Pool pool);

     void reback(Pool pool);

     void initTable();

     List<String> showTables();

     void addPrefix(@Param("prefix") String prefix);

     List<Pool> preOrSuf(PageForPool pageForPool);

     List<String> showField();

     void addConfig(@Param("time") String time);

     void setConfig(@Param("time") String time);

     Integer preOrSufCount(@Param("preOrSuf") String preOrSuf);
}