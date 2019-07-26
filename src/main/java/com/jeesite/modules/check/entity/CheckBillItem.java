/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.check.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 检测单表Entity
 * @author pengxincheng
 * @version 2019-06-22
 */
@Table(name="check_bill_item", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="bill_id", attrName="billId.id", label="维修单编号"),
		@Column(name="item_name", attrName="itemName", label="收费项目", queryType=QueryType.LIKE),
		@Column(name="item_id", attrName="itemId", label="项目编号"),
		@Column(name="num", attrName="num", label="数量"),
		@Column(name="price", attrName="price", label="价格"),
	}, orderBy="a.id ASC"
)
public class CheckBillItem extends DataEntity<CheckBillItem> {
	
	private static final long serialVersionUID = 1L;
	private CheckBill billId;		// 维修单编号 父类
	@NotBlank(message = "检测项目不能为空")
	private String itemName;		// 收费项目
	@NotNull(message = "检测项目不能为空")
	private String itemId;		// 项目编号
	private Long num;		// 数量
	private Double price;		// 价格
	
	public CheckBillItem() {
		this(null);
	}


	public CheckBillItem(CheckBill billId){
		this.billId = billId;
	}
	

	public CheckBill getBillId() {
		return billId;
	}

	public void setBillId(CheckBill billId) {
		this.billId = billId;
	}
	
	@Length(min=0, max=200, message="收费项目长度不能超过 200 个字符")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Length(min=0, max=64, message="项目编号长度不能超过 64 个字符")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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