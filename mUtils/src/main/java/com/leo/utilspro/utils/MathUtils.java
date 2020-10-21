package com.leo.utilspro.utils;

import java.math.BigDecimal;

/**
 * Created by leo
 * on 2020/10/19.
 */
public class MathUtils {
    private MathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //保留几位小数
    public static double round(Double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = null == v ? new BigDecimal("0.0") : new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
}
