package com.jeesite.modules.utils;

/**
 * @author pengxincheng
 * @date 2021/10/29 2:12 下午
 */
public class CarTypeUtil {

    private static final String BIG = "大";
    private static final String MIDDLE = "中";
    private static final String HEAVY = "重";

    private static final String SMALL = "小";
    private static final String LIGHT = "轻";
    private static final String TINY = "微";

    private static final String MO_TUO = "摩托";
    private static final String DI_SU = "低速";
    private static final String GUA = "挂";
    private static final String XUE = "学";
    private static final String JING = "警";

    private static final String SAN_LUN = "三轮";




    /**
     * 系统车型 转化为申请表中的车型
     *
     * @param sysCarType
     * @param plateNum
     * @return
     */
    public static String getPrintCarType(String sysCarType, String plateNum) {

        if (sysCarType.contains(SAN_LUN)) {
            return "三轮汽车";
        }

        if (sysCarType.contains(DI_SU)) {
            return "低速汽车";
        }

        if (sysCarType.contains(GUA) && plateNum.contains(GUA)) {
            return "挂车";
        }

        if (sysCarType.contains(MO_TUO)) {
            if (plateNum.contains(JING)) {
                return "警用摩托车";
            } else if (plateNum.contains(XUE)) {
                return "教练摩托车";
            } else {
                return "普通摩托车";
            }
        }


        if (sysCarType.contains(BIG) || sysCarType.contains(MIDDLE) || sysCarType.contains(HEAVY)) {
            if (plateNum.length() == 7) {
                return "大型汽车";
            } else {
                return "大型新能源机动车";
            }
        }

        if (sysCarType.contains(SMALL) || sysCarType.contains(LIGHT) || sysCarType.contains(TINY)) {

            if (plateNum.contains(XUE)) {
                return "教练汽车";
            }

            if (plateNum.contains(JING)) {
                return "警用汽车";
            }

            if (plateNum.length() == 7) {
                return "小型汽车";
            } else {
                return "小型新能源机动车";
            }
        }

        return "";
    }
}
