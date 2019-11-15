package com.lihang.selfmvvm.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leo on 2017/9/19.
 */

public class TimeUtils {

    /**
     * 时间戳 转为时间 只要传入格式即刻
     * 如："yyyy.MM.dd  HH:mm"    "yyyy-MM-dd"
     */
    public static String getMilToDateAll(String milSecond) {
        long lcc_time = Long.valueOf(milSecond);
        Date date = new Date(lcc_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
        return format.format(date);
    }


    /**
     * 时间  转换为  时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式  例如: yyyy-MM-dd
     * @return
     */
    public static long getTimeStamp(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 发布时间距离现在多久
     */
    public static String getTimesToNow(String milSecond) {
        if (TextUtils.isEmpty(milSecond)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = format.format(new Date());
        String returnText = null;
        long from = Long.parseLong(milSecond);
        try {
            long to = format.parse(now).getTime();
            int days = (int) ((to - from) / (1000 * 60 * 60 * 24));
            if (days == 0) {//一天以内，以分钟或者小时显示
                int hours = (int) ((to - from) / (1000 * 60 * 60));
                if (hours == 0) {
                    int minutes = (int) ((to - from) / (1000 * 60));
                    if (minutes == 0) {
                        returnText = "刚刚";
                    } else {
                        //解决Bug - 1分钟
                        if (minutes < 0) {
                            returnText = "刚刚";
                        } else {
                            returnText = minutes + "分钟前";
                        }
                    }
                } else if (hours < 0) {
                    returnText = "刚刚";
                } else {
                    returnText = hours + "小时前";
                }
            } else if (days == 1) {
                Date date = new Date(from);
                SimpleDateFormat format_zuo = new SimpleDateFormat("HH:mm");
                returnText = "昨天" + format_zuo.format(date);
            } else if (days >= 365) {
                Date date = new Date(from);
                SimpleDateFormat format_zuo = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                returnText = format_zuo.format(date);
            } else {
                Date date = new Date(from);
                SimpleDateFormat format_zuo = new SimpleDateFormat("MM-dd HH:mm");
                returnText = format_zuo.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnText;
    }

}
