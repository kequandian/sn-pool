package com.jfeat.am.module.NumberGenerator.config;

import com.jfeat.am.module.NumberGenerator.services.crud.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Init the pool when system is started.
 *
 * Created by jackyhuang on 2017/12/22.
 */
@Component
public class PoolInit implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private PoolService poolService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        poolService.initPool();
    }
}
