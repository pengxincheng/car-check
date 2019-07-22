package com.jeesite.modules.check.bo;

import com.jeesite.modules.check.entity.CheckBill;

/**
 * Created by pxc on 2019/7/19.
 */
public class CheckBillItemBO {
    private String itemName;		// 收费项目
    private String itemId;		// 项目编号
    private Long num;		// 数量
    private String price;		// 价格
    private String remarks;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
