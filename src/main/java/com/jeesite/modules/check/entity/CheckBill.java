/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.check.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.jeesite.modules.utils.DateUtils;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 检测单表Entity
 * @author pengxincheng
 * @version 2019-06-22
 */
@Table(name="check_bill", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="bill_id", attrName="billId", label="检测单号"),
		@Column(name="check_time", attrName="checkTime", label="检测时间", isInsert=false, isUpdate=false),
		@Column(name="plate_number", attrName="plateNumber", label="车牌号"),
		@Column(name="customer_id", attrName="customerId", label="客户编号"),
		@Column(name="customer_name", attrName="customerName", label="客户名称", queryType=QueryType.LIKE),
		@Column(name="customer_phone_number", attrName="customerPhoneNumber", label="联系方式", isQuery=false),
		@Column(name="customer_address", attrName="customerAddress", label="客户地址", isQuery=false),
		@Column(name="car_type", attrName="carType", label="车型"),
		@Column(name="total_amt", attrName="totalAmt", label="应收金额", isQuery=false),
		@Column(name="bill_type", attrName="billType", label="检测单类型", isQuery=false),
		@Column(name="ori_bill_id", attrName="oriBillId", label="原单号", isQuery=false),
		@Column(name="settle_type", attrName="settleType", label="是否结算", isQuery=false),
		@Column(name="remark_name", attrName="remarkName", label="备注人", isQuery=false),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.check_time DESC"
)
public class CheckBill extends DataEntity<CheckBill> {
	
	private static final long serialVersionUID = 1L;
	private String billId;		// 检测单号
	private Date checkTime;		// 检测时间
	private String plateNumber;		// 车牌号
	private String customerId;		// 客户编号
	private String customerName;		// 客户名称
	private String customerPhoneNumber;		// 联系方式
	private String customerAddress;		// 客户地址
	private String remarkName;
	private String carType;		// 车型
	private Double totalAmt;		// 应收金额
	private String totalAmtStr;
	private int billType;
	private String oriBillId;
	private Integer settleType;
	private List<CheckBillItem> checkBillItemList = ListUtils.newArrayList();		// 子表列表
	
	public CheckBill() {
		this(null);
	}

	public CheckBill(String id){
		super(id);
	}
	
	@NotBlank(message="检测单号不能为空")
	@Length(min=0, max=64, message="检测单号长度不能超过 64 个字符")
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	@NotBlank(message="车牌号不能为空")
	@Length(min=0, max=20, message="车牌号长度不能超过 20 个字符")
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	@NotBlank(message="客户编号不能为空")
	@Length(min=0, max=64, message="客户编号长度不能超过 64 个字符")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=100, message="客户名称长度不能超过 100 个字符")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	
	@Length(min=0, max=200, message="客户地址长度不能超过 200 个字符")
	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	@NotBlank(message="车型不能为空")
	@Length(min=0, max=100, message="车型长度不能超过 100 个字符")
	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	@NotNull(message="应收金额不能为空")
	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}
	
	public Date getCheckTime_gte() {
		return sqlMap.getWhere().getValue("check_time", QueryType.GTE);
	}

	public void setCheckTime_gte(Date checkTime) {
		sqlMap.getWhere().and("check_time", QueryType.GTE, checkTime);
	}
	
	public Date getCheckTime_lte() {
		return sqlMap.getWhere().getValue("check_time", QueryType.LTE);
	}

	public void setCheckTime_lte(Date checkTime) {
		sqlMap.getWhere().and("check_time", QueryType.LTE, DateUtils.getMaxDateOfDay(checkTime));
	}

	@Valid
	@NotNull(message = "检测项目不能为空")
	@Size(message = "检测项目不能为空")
	public List<CheckBillItem> getCheckBillItemList() {
		return checkBillItemList;
	}

	public void setCheckBillItemList(List<CheckBillItem> checkBillItemList) {
		this.checkBillItemList = checkBillItemList;
	}

	public int getBillType() {
		return billType;
	}

	public void setBillType(int billType) {
		this.billType = billType;
	}

	public String getOriBillId() {
		return oriBillId;
	}

	public void setOriBillId(String oriBillId) {
		this.oriBillId = oriBillId;
	}

	public Integer getSettleType() {
		return settleType;
	}

	public void setSettleType(Integer settleType) {
		this.settleType = settleType;
	}

	public String getTotalAmtStr() {
		return totalAmtStr;
	}

	public void setTotalAmtStr(String totalAmtStr) {
		this.totalAmtStr = totalAmtStr;
	}

	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}
}