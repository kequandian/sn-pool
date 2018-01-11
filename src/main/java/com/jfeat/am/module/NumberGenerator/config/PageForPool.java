package com.jfeat.am.module.NumberGenerator.config;

/**
 * Created by Silent-Y on 2017/12/18.
 */
public class PageForPool {
    private long index;
    private long pageSize;
    private String preOrSuf;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public String getPreOrSuf() {
        return preOrSuf;
    }

    public void setPreOrSuf(String preOrSuf) {
        this.preOrSuf = preOrSuf;
    }
}
