package com.leo.utilspro.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leo on 2017/9/19.
 */

public class TimeUtils {

    /**
     * 时间戳 转时间。
     *
     * @param timeMillis 时间戳
     * @param formatStr  要转换的时间格式;如："yyyy.MM.dd  HH:mm"    "yyyy-MM-dd"
     * @return
     */
    public static String getStrByLong(long timeMillis, String formatStr) {
        Date date = new Date(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }


    /**
     * 时间  转换为  时间戳
     *
     * @param timeStr   时间字符串 例如: 2016-03-09
     * @param formatStr 时间对应格式  例如: yyyy-MM-dd
     * @return
     */
    public static long getLongByStr(String timeStr, String formatStr) {
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
    public static String getTimeFromNow(long milSecond) {
        String timeStr = TimeUtils.getStrByLong(milSecond, "yyyy-MM-dd");
        long now = System.currentTimeMillis();
        long fromSeconds = milSecond;

        if (isToday(timeStr)) {
            //如果是今天
            //一天以内，以分钟， 小时， 刚刚显示
            int hours = (int) ((now - fromSeconds) / (1000 * 60 * 60));
            if (hours == 0) {
                int minutes = (int) ((now - fromSeconds) / (1000 * 60));
                if (minutes <= 0) {
                    return "刚刚";
                } else {
                    return minutes + "分钟前";
                }
            } else {
                return hours + "小时前";
            }
        } else {
            String yesterday = getLastDate(-1);
            if (timeStr.equals(yesterday)) {
                //如果是昨天的日期
                String yesterdayTime = TimeUtils.getStrByLong(milSecond, "HH:mm");
                return "昨天 " + yesterdayTime;
            } else {
                //如果不是昨天看看是否是今年的数据
                String yearNumber_now = TimeUtils.getStrByLong(System.currentTimeMillis(), "yyyy");
                String yearNumber = TimeUtils.getStrByLong(milSecond, "yyyy");
                if (yearNumber_now.equals(yearNumber)) {
                    //说明是今年
                    String yearTimeStr = TimeUtils.getStrByLong(milSecond, "MM-dd HH:mm");
                    return yearTimeStr;
                } else {
                    String yearTimeStr = TimeUtils.getStrByLong(milSecond, "yyyy-MM-dd HH:mm");
                    return yearTimeStr;
                }
            }
        }
    }


    /**
     * 是否是今天
     */
    public static boolean isToday(String targetDate) {
        String todayStr = TimeUtils.getStrByLong(System.currentTimeMillis(), "yyyy-MM-dd");
        return todayStr.equals(targetDate);
    }

    /**
     * 个位数补0操作
     */
    public static String getValue(int num) {
        return String.valueOf(num > 9 ? num : ("0" + num));
    }


    public static String getLastDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, days);
        String targetDate = getValue(cal.get(cal.YEAR)) + "-" + getValue(cal.get(cal.MONTH) + 1) + "-" + getValue(cal.get(cal.DATE));
        return targetDate;
    }


}
