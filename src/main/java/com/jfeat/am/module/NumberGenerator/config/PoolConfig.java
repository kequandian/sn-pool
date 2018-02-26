package com.jfeat.am.module.NumberGenerator.config;

import org.springframework.beans.factory.annotation.Value;
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

    private String length;

    //存放所有前缀
    private List<String> prefixes = new ArrayList<>();

    //存放所有的后缀
    private List<String> suffixes = new ArrayList<>();

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


    public PoolConfig(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

}
