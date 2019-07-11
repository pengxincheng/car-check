package com.jeesite.modules.check.vo;

/**
 * @description
 * @author: pengxincheng
 * @date: 2019-07-09 11:50
 */
public class CheckBillStatisticsVo {
    private Integer totalCount = 0;
    private Double totalAmt = 0.00;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }
}
