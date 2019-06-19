/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.customer.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 客户表Entity
 * @author pengxincheng
 * @version 2019-06-18
 */
@Table(name="customer", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="客户名称", queryType=QueryType.LIKE),
		@Column(name="plate_number", attrName="plateNumber", label="车牌号"),
		@Column(name="address", attrName="address", label="客户地址", isQuery=false),
		@Column(name="car_type", attrName="carType", label="车型"),
		@Column(name="phone_number", attrName="phoneNumber", label="联系电话"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class Customer extends DataEntity<Customer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 客户名称
	private String plateNumber;		// 车牌号
	private String address;		// 客户地址
	private String carType;		// 车型
	private Long phoneNumber;		// 联系电话
	
	public Customer() {
		this(null);
	}

	public Customer(String id){
		super(id);
	}
	
	@NotBlank(message="客户名称不能为空")
	@Length(min=0, max=64, message="客户名称长度不能超过 64 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank(message="车牌号不能为空")
	@Length(min=0, max=20, message="车牌号长度不能超过 20 个字符")
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	@Length(min=0, max=255, message="客户地址长度不能超过 255 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=64, message="车型长度不能超过 64 个字符")
	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	@NotNull(message="联系电话不能为空")
	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}