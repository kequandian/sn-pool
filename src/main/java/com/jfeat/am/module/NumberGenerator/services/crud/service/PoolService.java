package com.jfeat.am.module.NumberGenerator.services.crud.service;
            
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;

import com.jfeat.am.module.NumberGenerator.config.PoolConfig;

import java.util.Date;


/**
 * <p>
 * 订单池 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-12-05
 */

public interface PoolService   {
         void initPool();

         String getSerialNumber(boolean isRandom, PoolConfig poolConfig);

         String getSerialNumber();

         String getSerialNumber(String prefix);

         void backupData();

         String formatDateSpecial(Date date);

         void rollback(String num);
}
