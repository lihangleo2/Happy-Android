package com.lihang.selfmvvm.utils;

import android.util.Log;

/**
 * Created by leo on 2017/9/12.
 */

public class LogUtils {

    //可以全局控制是否打印log日志
    private static boolean isEnableLog = true;
    private static int LOG_MAXLENGTH = 4000;
    public static final String TAG = LogUtils.class.getSimpleName();
    private static final String TOP_DIVIDER =
            "┌────────────────────────────────────────────────────────";

    private static final String BOTTOM_DIVIDER =
            "└────────────────────────────────────────────────────────";
    private static final String MIDDLE_DIVIDER = "----------- 换行 ------------\n";

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tagName, String msg) {
        if (isEnableLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            if (strLength > end) {
                StringBuffer sbf = new StringBuffer();
                sbf.append(" \n" + TOP_DIVIDER).append(msg);
                String trueMsg = sbf.toString();
                strLength = trueMsg.length();
                while (strLength > end) {
                    if (start == 0) {
                        Log.v(tagName, trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    } else {
                        end = end - MIDDLE_DIVIDER.length();
                        Log.v(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    }
                }
                Log.v(tagName, trueMsg.substring(start, strLength));
                Log.v(tagName, " \n" + BOTTOM_DIVIDER);
            } else {
                Log.v(tagName, msg);
            }
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tagName, String msg) {
        if (isEnableLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            if (strLength > end) {
                StringBuffer sbf = new StringBuffer();
                sbf.append(" \n" + TOP_DIVIDER).append(msg);
                String trueMsg = sbf.toString();
                strLength = trueMsg.length();
                while (strLength > end) {
                    if (start == 0) {
                        Log.d(tagName, trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    } else {
                        end = end - MIDDLE_DIVIDER.length();
                        Log.d(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    }
                }
                Log.d(tagName, trueMsg.substring(start, strLength));
                Log.d(tagName, " \n" + BOTTOM_DIVIDER);
            } else {
                Log.d(tagName, msg);
            }
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }


    public static void i(String tagName, String msg) {
        if (isEnableLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            if (strLength > end) {
                StringBuffer sbf = new StringBuffer();
                sbf.append(" \n" + TOP_DIVIDER).append(msg);
                String trueMsg = sbf.toString();
                strLength = trueMsg.length();
                while (strLength > end) {
                    if (start == 0) {
                        Log.i(tagName, trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    } else {
                        end = end - MIDDLE_DIVIDER.length();
                        Log.i(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    }
                }
                Log.i(tagName, trueMsg.substring(start, strLength));
                Log.i(tagName, " \n" + BOTTOM_DIVIDER);
            } else {
                Log.i(tagName, msg);
            }
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tagName, String msg) {
        if (isEnableLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            if (strLength > end) {
                StringBuffer sbf = new StringBuffer();
                sbf.append(" \n" + TOP_DIVIDER).append(msg);
                String trueMsg = sbf.toString();
                strLength = trueMsg.length();
                while (strLength > end) {
                    if (start == 0) {
                        Log.w(tagName, trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    } else {
                        end = end - MIDDLE_DIVIDER.length();
                        Log.w(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    }
                }
                Log.w(tagName, trueMsg.substring(start, strLength));
                Log.w(tagName, " \n" + BOTTOM_DIVIDER);
            } else {
                Log.w(tagName, msg);
            }
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tagName, String msg) {
        if (isEnableLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            if (strLength > end) {
                StringBuffer sbf = new StringBuffer();
                sbf.append(" \n" + TOP_DIVIDER).append(msg);
                String trueMsg = sbf.toString();
                strLength = trueMsg.length();
                while (strLength > end) {
                    if (start == 0) {
                        Log.e(tagName, trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    } else {
                        end = end - MIDDLE_DIVIDER.length();
                        Log.e(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    }
                }
                Log.e(tagName, trueMsg.substring(start, strLength));
                Log.e(tagName, " \n" + BOTTOM_DIVIDER);
            } else {
                Log.e(tagName, msg);
            }
        }
    }
}
