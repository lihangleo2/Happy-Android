package com.leo.utilspro.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leo on 2017/9/19.
 */

public class TimeUtils {

    /**
     * 时间戳 转时间。
     * @param timeMillis 时间戳
     * @param formatStr 要转换的时间格式;如："yyyy.MM.dd  HH:mm"    "yyyy-MM-dd"
     * @return
     */
    public static String getLongToStr(long timeMillis,String formatStr) {
        Date date = new Date(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }


    /**
     * 时间  转换为  时间戳
     *
     * @param timeStr 时间字符串 例如: 2016-03-09
     * @param formatStr  时间对应格式  例如: yyyy-MM-dd
     * @return
     */
    public static long getTimeStamp(String timeStr, String formatStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
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
     * 发布时间距离现在多久。要展示的文案
     */
    public static String getFromNow(long milSecond) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowStr = format.format(new Date());
        //当前时间
        try {
            long now = format.parse(nowStr).getTime();
            long tag = milSecond;
            int days = (int) ((now - tag) / (1000 * 60 * 60 * 24));
            if (days == 0) {
                //一天以内，以分钟， 小时， 刚刚显示
                int hours = (int) ((now - tag) / (1000 * 60 * 60));
                if (hours == 0) {
                    int minutes = (int) ((now - tag) / (1000 * 60));
                    if (minutes <= 0) {
                        return "刚刚";
                    } else {
                        return minutes + "分钟前";
                    }
                } else {
                    return hours + "小时前";
                }
            } else if (days >= 1 && days <= 7) {
                return days + "天前";
            } else if (days >= 365) {
                Date date = new Date(tag);
                SimpleDateFormat format_zuo = new SimpleDateFormat("yyyy-MM-dd");
                return format_zuo.format(date);
            } else {
                Date date = new Date(tag);
                SimpleDateFormat format_zuo = new SimpleDateFormat("MM-dd");
                return format_zuo.format(date);
            }


        } catch (ParseException e) {
            return "";
        }
    }


}
