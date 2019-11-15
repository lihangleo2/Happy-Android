package com.lihang.selfmvvm.utils;

import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leo
 * on 2019/11/15.
 * 一些比较杂的工具类集合
 */
public class LeoUtils {
    private LeoUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断号码是否是手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhoneNumber(String mobiles) {
        if (mobiles.length() != 11) {
            return false;
        }
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 判断身份证号是否正确（其中包括了月份和日期进行了判断）
     * @param text
     * @return
     */
    public static boolean isIDcards(String text) {
        Pattern p = Pattern
                .compile("^\\d{6}(18|19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$");
        Matcher m = p.matcher(text);
        return m.matches();
    }


    /**
     * 保留几位小数点
     * @param v
     * @param scale
     * @return
     */
    public static double round(Double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = null == v ? new BigDecimal("0.0") : new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }



    /**
     * 固定宽度情况下，自适应文本字体大小
     * @param tv
     * @param maxWidth
     * @param text
     * @return
     */
    public static float adjustTvTextSize(TextView tv, int maxWidth, String text) {
        int avaiWidth = maxWidth - tv.getPaddingLeft() - tv.getPaddingRight() - 10;

        if (avaiWidth <= 0 || TextUtils.isEmpty(text)) {
            return tv.getPaint().getTextSize();
        }

        TextPaint textPaintClone = new TextPaint(tv.getPaint());
        // note that Paint text size works in px not sp
        float trySize = textPaintClone.getTextSize();

        while (textPaintClone.measureText(text) > avaiWidth) {
            trySize--;
            textPaintClone.setTextSize(trySize);
        }

        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        tv.setText(text);
        return trySize;
    }





    //空数据时候，占位图
    public static void checkEmpty(int size, View view) {
        if (size > 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
