package com.jfeat.am.module.NumberGenerator.services.crud.service;
            
import com.jfeat.am.module.NumberGenerator.services.persistence.model.Pool;

import com.jfeat.am.common.crud.CRUDServiceOnly;
import com.jfeat.am.module.NumberGenerator.services.persistence.model.PoolConfig;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 订单池 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-12-05
 */

public interface PoolService  extends CRUDServiceOnly<Pool> {
        public void initPool();

        public String getSerialNumber(boolean isFlag,PoolConfig poolConfig);

        public void backupData();

        public String formatDateSpecial(Date date);

        public void reback(String num);
}
