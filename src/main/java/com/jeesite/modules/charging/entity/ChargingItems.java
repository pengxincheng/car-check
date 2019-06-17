/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.charging.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 检查项目表Entity
 * @author pengxincheng
 * @version 2019-06-16
 */
@Table(name="charging_items", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="item_name", attrName="itemName", label="项目名称", queryType=QueryType.LIKE),
		@Column(name="num", attrName="num", label="数量"),
		@Column(name="price", attrName="price", label="金额"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ChargingItems extends DataEntity<ChargingItems> {
	
	private static final long serialVersionUID = 1L;
	private String itemName;		// 项目名称
	private Long num;		// 数量
	private Double price;		// 金额
	
	public ChargingItems() {
		this(null);
	}

	public ChargingItems(String id){
		super(id);
	}
	
	@Length(min=0, max=100, message="项目名称长度不能超过 100 个字符")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}