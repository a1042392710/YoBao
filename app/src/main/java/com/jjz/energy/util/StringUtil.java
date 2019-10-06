package com.jjz.energy.util;

import com.blankj.utilcode.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  字符串工具类
 * Date: 2018/12/19 下午1:53
 */
public class StringUtil {

    /**
     * 限定只能小数点后两位
     */
    public static boolean isCorrectDecimalAndLenth(String s,int lenth) {


        //小数点后不能超过两位
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                ToastUtils.showShort("小数后最后两位");
             return false;
            }
        }
        //长度不能超过指定位数
        if (s.length()>lenth){
            return false;
        }

        return true;
    }

    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * HTML转义字符 --> 字符串
     */
    public static String htmlEscapeCharsToString(String source) {
        return isEmpty(source) ? source : source.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"")
                .replaceAll("&copy;", "")
                .replaceAll("&yen;", "¥")
                .replaceAll("&divide;", "÷")
                .replaceAll("&times;", "×")
                .replaceAll("&reg;", "")
                .replaceAll("&sect;", "§")
                .replaceAll("&pound;", "£")
                .replaceAll("&cent;", "￠");
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)||"null".equals(input))
            return true;


        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 转换字符编码的工具类
     *
     * 将字符编码转换成UTF-8
     */
    public static String toUTF_8(String str) throws UnsupportedEncodingException {
        return changeCharset(str, "UTF-8");// 8位 UCS 转换格式
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换的字符串
     * @param newCharset 目标编码
     */
    public static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
            byte[] bs = str.getBytes();
            return new String(bs, newCharset);// 用新的字符编码生成字符串
        }
        return "";
    }

    /**
     * 判断集合是否为空
     * @param list 集合
     * @param <T> 类型
     * @return 是/否
     */
    public  static <T>boolean isListEmpty(List<T> list){
        if (null!=list&&list.size()>0){
            return false;
        }
        return true;
    }
    /**
     * 检验手机号码 ，只判断长度
     *
     * @param mobileNums
     * @return 是否
     */
    public static boolean isMobile(String mobileNums) {
        if (mobileNums.length() == 11 && mobileNums.matches("[0-9]{1,}")) {
            return true;
        }
        return false;
    }
}
