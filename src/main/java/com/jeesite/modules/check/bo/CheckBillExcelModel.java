package com.jeesite.modules.check.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;

/**
 * @description
 * @author: pengxincheng
 * @date: 2019-06-24 13:46
 */
public class CheckBillExcelModel extends BaseRowModel {

    @ExcelProperty(value = "车牌号", index = 0)
    private String plateNumber;        // 车牌号

    @ExcelProperty(value = "检测单号", index = 1)
    private String billId;        // 检测单号

    @ExcelProperty(value = "检测时间", index = 2)
    private String checkTime;        // 检测时间

    @ExcelProperty(value = "客户编号", index = 3)
    private String customerId;        // 客户编号

    @ExcelProperty(value = "项目编号", index = 4)
    private String itemId;        // 项目编号

    @ExcelProperty(value = "客户名称", index = 5)
    private String customerName;        // 客户名称

    @ExcelProperty(value = "车型", index = 6)
    private String carType;        // 车型

    @ExcelProperty(value = "检测项目", index = 7)
    private String itemName;        // 收费项目

    @ExcelProperty(value = "数量", index = 8)
    private Long num;        // 数量

    @ExcelProperty(value = "金额", index = 9)
    private Double price;        // 价格

    @ExcelProperty(value = "联系地址", index = 10)
    private String customerAddress;        // 客户地址

    @ExcelProperty(value = "联系方式", index = 11)
    private Long customerPhoneNumber;        // 联系方式

    @ExcelProperty(value = "备注", index = 12)
    private String remark;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Long getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(Long customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
