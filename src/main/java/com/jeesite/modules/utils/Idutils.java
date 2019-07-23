package com.jeesite.modules.utils;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @description
 * @author: pengxincheng
 * @date: 2019-06-22 15:24
 */
public class Idutils {

    public static final String PREFIX = "WX";
    public static final String CUSTOMER_PREFIX = "K";

    /**
     * 获取下一个检测单编号
     *
     * @param currentCount 当月目前记录数
     * @return
     */
    public static String getNextCheckBillId(int currentCount) {
        currentCount += 1;
        StringBuffer sb = new StringBuffer(PREFIX);
        sb.append(DateUtils.getStringFromDate(new Date(), DateUtils.FORMAT_YEAR_MONTH)).append("-");
        String end = new DecimalFormat("0000").format(currentCount);
        return sb.append(end).toString();
    }

    /**
     * 获取下一个客户编号
     *
     * @return
     */
    public static String getNextCustomerCode(int currentCount) {
        currentCount += 1;
        StringBuffer sb = new StringBuffer(CUSTOMER_PREFIX);
        sb.append(new DecimalFormat("00000000").format(currentCount));
        return sb.toString();
    }

    public static String getNextItemCode(int currentCount){
        currentCount += 1;
        StringBuffer sb = new StringBuffer(PREFIX);
        sb.append(new DecimalFormat("000").format(currentCount));
        return sb.toString();
    }

}
