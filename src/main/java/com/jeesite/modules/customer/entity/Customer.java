/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.customer.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

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
		@Column(name="code", attrName="code", label="客户编码"),
		@Column(name = "agent_name",attrName = "agentName",label = "代理人姓名"),
		@Column(name = "remark_name",attrName = "remarkName",label = "代理人姓名"),
		@Column(name = "apply_type",attrName = "applyType",label = "申请方式"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class Customer extends DataEntity<Customer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 客户名称
	private String plateNumber;		// 车牌号
	private String address;		// 客户地址
	private String carType;		// 车型
	private String phoneNumber;		// 联系电话
	private String code;    //客户编码
	private String agentName;
	private String remarkName;
	private Integer applyType;

	/**
	 * 非数据库实体
	 */
	private String agentPhone;

	/**
	 * 非数据库实体
	 * 事件  yyyy年mm月dd日
	 */
	private String date;

	/**
	 * 控制本地申请的勾选
	 * 非数据库实体
	 */
	private String local;

	/**
	 * 控制外地申请的勾选
	 * 非数据库实体
	 */
	private String notLocal;


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
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getApplyType() {
		return applyType;
	}

	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getNotLocal() {
		return notLocal;
	}

	public void setNotLocal(String notLocal) {
		this.notLocal = notLocal;
	}
}