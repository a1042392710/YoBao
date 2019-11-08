package com.jjz.energy.util;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Features: 日期管理工具类
 * @author: create by chenhao on 2019/10/6
 */
public class DateUtil  {
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
    public static String stampToDateDay(String s) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        long lcc = Long.valueOf(s);
        int i = Integer.parseInt(s);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * long转日期
     */
    public static String longToDate(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time*1000L));
    }

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 计算两个时间戳 相差的时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static String dateDiff(long startTime, long endTime) {
        // 按照传入的格式生成一个simpledateformate对象
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        // 获得两个时间的毫秒时间差异
        diff = endTime
                - startTime;
        day = diff / nd;// 计算差多少天
        long hour = diff % nd / nh;// 计算差多少小时
        long min = diff % nd % nh / nm;// 计算差多少分钟
        long sec = diff % nd % nh % nm / ns;// 计算差多少秒
        // 输出结果
        return day + "天" + hour + "小时" + min
                + "分钟";

    }
}
