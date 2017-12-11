package com.jfeat.am.module.NumberGenerator.services.persistence.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Silent-Y on 2017/12/4.
 */
@Component("PoolConfig")
public class PoolConfig {
    @Value("${PoolConfig.prefix}")
    private String prefix;
    @Value("${PoolConfig.suffix}")
    private String suffix;
    @Value("${PoolConfig.minlength}")
    private String length;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public PoolConfig() {
    }

    public String getSuffix() {

        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {

        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


}
