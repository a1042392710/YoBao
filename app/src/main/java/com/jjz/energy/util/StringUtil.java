package com.jjz.energy.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Display;
import android.view.WindowManager;

import com.blankj.utilcode.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by: xy
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
     * 判断手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        if (mobiles.length() == 11 && mobiles.matches("[0-9]{1,}")){
            return true;
        }
//        mobiles.matches("(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}");
//        Pattern p = Pattern.compile("(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}");
//        Matcher m = p.matcher("");
        return false;
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
     * 将时间转换为时间戳
     */
    public static String dateToStampStr(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        res = String.valueOf(date.getTime());
//        res = res.substring(0, 10);
        long ts = new BigInteger(res).longValue()/1000;
        return ts+"";
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        long lcc = Long.valueOf(s);
        int i = Integer.parseInt(s);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }


    /**
     * 将时间戳转换为时间
     */
    public static String stampToDateMinite(String s) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        long lcc = Long.valueOf(s);
        int i = Integer.parseInt(s);
        String times = sdr.format(new Date(i * 1000L));
        return times;
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
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        return display.getWidth();
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
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


    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;

                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


}
