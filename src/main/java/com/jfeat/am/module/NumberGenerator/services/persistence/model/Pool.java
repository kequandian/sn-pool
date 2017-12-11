package com.jfeat.am.module.NumberGenerator.services.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单池
 * </p>
 *
 * @author Code Generator
 * @since 2017-12-05
 */
@TableName("orderPool")
public class Pool extends Model<Pool> {

    private static final long serialVersionUID = 1L;

    /**
     * 单号
     */
	private String number;
    /**
     * 是否已经使用
     */
	@TableField("isUsed")
	private Integer isUsed;


	public String getNumber() {
		return number;
	}

	public Pool setNumber(String number) {
		this.number = number;
		return this;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public Pool setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
		return this;
	}

	public static final String NUMBER = "number";

	public static final String ISUSED = "isUsed";


	@Override
	public String toString() {
		return "Pool{" +
			"number=" + number +
			", isUsed=" + isUsed +
			"}";
	}

	@Override
	protected Serializable pkVal() {
		return null;
	}
}
