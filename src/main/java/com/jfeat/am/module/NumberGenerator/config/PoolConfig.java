package com.jfeat.am.module.NumberGenerator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silent-Y on 2017/12/4.
 */
@Component("PoolConfig")
@ConfigurationProperties(prefix="PoolConfig")
public class PoolConfig {
    private String prefix;
    private String suffix;
    private String minlength;
    private boolean hasKey;
    private String dateFormat;

    //存放所有前缀
    private List<String> prefixes = new ArrayList<>();

    //存放所有的后缀
    private List<String> suffixes = new ArrayList<>();

    public boolean getHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public List<String> getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(List<String> suffixes) {
        this.suffixes = suffixes;
    }

    public List<String> getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(List<String> prefixes) {
        this.prefixes = prefixes;
    }

    public String getMinlength() {
        return minlength;
    }

    public void setMinlength(String minlength) {
        this.minlength = minlength;
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


    public PoolConfig(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
