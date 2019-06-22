/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * 项目自定义的公共JavaScript，可覆盖jeesite.js里的方法
 */
jQuery.validator.addMethod("carNumber", function(value, element) {
    var regex = /^[京津渝沪冀晋辽吉黑苏浙皖闽赣鲁豫鄂湘粤琼川贵云陕秦甘陇青台内蒙古桂宁新藏澳军海北空航警使临][A-Z0-9][0-9A-Z]{5}$/;
    var pattern = regex.test(value);  //普通，军车

    var regexJGX = /^[京津渝沪冀晋辽吉黑苏浙皖闽赣鲁豫鄂湘粤琼川贵云陕秦甘陇青台内蒙古桂宁新藏澳]{1}[A-Z0-9]{1}[0-9a-zA-Z]{4}[挂警学]{1}$/;
    var patternJGX = regexJGX.test(value);//警，挂，学

    var regexWJ = /^WJ[京津渝沪冀晋辽吉黑苏浙皖闽赣鲁豫鄂湘粤琼川贵云陕秦甘陇青台内蒙古桂宁新藏澳]?[0-9a-zA-Z]{5}$/;
    var patternWJ = regexWJ.test(value);//武警

    var regexS = /^[0-9a-zA-Z]{6}[使]{1}$/;
    var patternS = regexS.test(value);//使馆车

    var regexNew = /^[京津渝沪冀晋辽吉黑苏浙皖闽赣鲁豫鄂湘粤琼川贵云陕秦甘陇青台内蒙古桂宁新藏澳][A-Z0-9][0-9A-Z]{6}$/;
    var patternNew = regexNew.test(value);  //新能源车

    var regJun = /^军[0-9A-Z]{4}$/;
    var patternJun = regJun.test(value);

    if(pattern || patternJGX || patternWJ || patternS || patternNew || patternJun){
        return true;
    }else {
        return false;
    }
}, "请输入正确的车牌号");