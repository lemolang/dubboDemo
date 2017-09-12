package com.jtd.ticketing.base.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunll on 2017/4/5.
 */
public class Validate {

    private static String AUTH_TYPE_EMAIL = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";//邮箱验证
    private static String AUTH_TYPE_PHONE = "^((\\d{3,4}\\-)|)\\d{7,8}(|([-\\u8f6c]{1}\\d{1,5}))$";//电话验证
    private static String AUTH_TYPE_MOBILE = "1[3-8]\\d{9}";//手机号验证
    private static String AUTH_TYPE_DIGITS = "^\\d+$";//必须输入整数。
    private static String AUTH_TYPE_NUMBER = "^(?:-?\\d+|-?\\d{1,3}(?:,\\d{3})+)?(?:\\.\\d+)?$";//	必须输入合法的数字（负数，小数）。
    private static String AUTH_TYPE_POSTCODE = "^[0-9]{6}$";//邮政编码验证

    //客户模块另需验证 by Wz
    private static String AUTH_TYPE_SALESVOLUME = "^[0-9]{0,12}$";//销售额验证
    private static String AUTH_TYPE_PROMOTIONNUMBER = "^[0-9]{0,6}$";//编号验证
    private static String AUTH_TYPE_WORKERTOTAL = "^[0-9]{0,20}$";//员工数验证


    //表单验证：长度验证
    public static final int LENGTH_VALIDATE_SIX = 6;
    public static final int LENGTH_VALIDATE_TEN = 10;
    public static final int LENGTH_VALIDATE_TWENTY = 20;
    public static final int LENGTH_VALIDATE_THIRTY = 30;
    public static final int LENGTH_VALIDATE_TWELVE = 12;
    public static final int LENGTH_VALIDATE_FIFTEEN = 15;
    public static final int LENGTH_VALIDATE_FIFTY = 50;
    public static final int LENGTH_VALIDATE_SIXTY = 60;


    /**
     * 必填验证
     *
     * @param str 待验证的字符串
     * @return 验证结果
     */
    public static boolean isRequired(String str) {
        if (StringUtils.isNotBlank(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 最大长度验证
     *
     * @param str    待验证的字符串
     * @param length 最大长度
     * @return 验证结果
     */
    public static boolean maxLength(String str, int length) {
        if (StringUtils.isNotBlank(str.trim())) {
            if (str.trim().length() <= length) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * 最小长度验证
     *
     * @param str    待验证的字符串
     * @param length 最小长度
     * @return 验证结果
     */
    public static boolean minLength(String str, int length) {
        if (StringUtils.isNotBlank(str.trim())) {
            if (str.trim().length() >= length) {
                return true;
            }
        }
        return false;
    }

    /**
     * Email验证
     *
     * @param str 待验证的字符串
     * @return 验证结果
     */
    public static boolean isEmail(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_EMAIL, str);
        }
        return true;
    }

    /**
     * 电话号验证
     *
     * @param str 待验证的电话号
     * @return 验证结果
     */
    public static boolean isPhone(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_PHONE, clearSpace(str));
        }
        return true;
    }

    /**
     * 手机号验证
     *
     * @param str 待验证的手机号
     * @return 验证结果
     */
    public static boolean isMobile(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_MOBILE, clearSpace(str));
        }
        return true;
    }

    /**
     * 整数验证
     *
     * @param str 待验证的字符串
     * @return 验证结果
     */
    public static boolean isDigits(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_DIGITS, str);
        }
        return false;
    }

    /**
     * 数字验证
     * 必须输入合法的数字（负数，小数）
     *
     * @param str 待验证的字符串
     * @return 验证结果
     */
    public static boolean isNumber(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_NUMBER, str);
        }
        return false;
    }

    /**
     * 邮编验证
     *
     * @param str 待验证的字符串
     * @return 验证结果
     */
    public static boolean isPostcode(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_POSTCODE, str);
        }
        return true;
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String str = "1864101808";
        System.out.println(match("1[3-8]\\d{9}", str));
    }

    //客户模块另需验证 by Wz

    /**
     * 销售额验证
     *
     * @param str 待验证的字符串
     * @return 验证结果
     */
    public static boolean isSalesVolume(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_SALESVOLUME, str);
        }
        return true;
    }

    /**
     * 推广编号验证
     *
     * @param str 待验证的字符串
     * @return 验证结果
     */
    public static boolean isPromotionNumber(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_PROMOTIONNUMBER, str);
        }
        return true;
    }

    /**
     * 员工数验证
     *
     * @param str 待验证的字符串
     * @return 验证结果
     */
    public static boolean isWorkerTotal(String str) {
        if (StringUtils.isNotBlank(str)) {
            return match(AUTH_TYPE_WORKERTOTAL, str);
        }
        return true;
    }

    /**
     * 去除空格、换行符、制表符
     */
    public static String clearSpace(String str){
        String returnStr = "";
        if (StringUtils.isNotBlank(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            returnStr = m.replaceAll("");
        }
        return returnStr;
    }
}
