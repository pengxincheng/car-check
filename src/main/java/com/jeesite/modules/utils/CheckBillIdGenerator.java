package com.jeesite.modules.utils;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @description
 * @author: pengxincheng
 * @date: 2019-06-22 15:24
 */
public class CheckBillIdGenerator {

    private static final String PREFIX = "WX";

    /**
     * 获取下一个编号
     * @param currentCount 当月目前记录数
     * @return
     */
    public static String getNextId(int currentCount) {
        currentCount += 1;
        StringBuffer sb = new StringBuffer(PREFIX);
        sb.append(DateUtils.getStringFromDate(new Date(), DateUtils.FORMAT_YEAR_MONTH)).append("-");
        String end = new DecimalFormat("0000").format(currentCount);
        return sb.append(end).toString();
    }

}
