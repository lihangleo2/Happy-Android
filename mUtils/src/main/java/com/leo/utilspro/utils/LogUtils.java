package com.leo.utilspro.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by leo on 2017/9/12.
 */

public class LogUtils {

    //可以全局控制是否打印log日志
    private static boolean isEnableLog = true;
    private static int LOG_MAXLENGTH = 3000;
    public static final String TAG = LogUtils.class.getSimpleName();


    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tagName, String msg) {
        if (isEnableLog) {
            //如果是null
            if (msg == null) {
                Log.v(tagName, "null");
                return;
            }

            //如果是空的话
            if (TextUtils.isEmpty(msg)) {
                Log.v(tagName, "\"\"");
                return;
            }


            int strLength = msg.length();
            if (strLength > LOG_MAXLENGTH) {
                //如果打印长度大于，限制的最大长度后，我们就需要分段打印
                //已经打印的长度
                int printLenght = 0;
                //初始化一个开始打印的index
                int printStart = 0;
                //总长度大于已打印的长度，那么就一直打印
                while (strLength > printLenght) {

                    if ((printStart + LOG_MAXLENGTH) <= strLength) {
                        Log.v(tagName, msg.substring(printStart, printStart + LOG_MAXLENGTH));
                        printLenght = printStart + LOG_MAXLENGTH;
                        printStart = printStart + LOG_MAXLENGTH;
                    } else {
                        Log.v(tagName, msg.substring(printStart, strLength));
                        printLenght = strLength;
                    }
                }

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
            //如果是null
            if (msg == null) {
                Log.d(tagName, "null");
                return;
            }

            //如果是空的话
            if (TextUtils.isEmpty(msg)) {
                Log.d(tagName, "\"\"");
                return;
            }


            int strLength = msg.length();
            if (strLength > LOG_MAXLENGTH) {
                //如果打印长度大于，限制的最大长度后，我们就需要分段打印
                //已经打印的长度
                int printLenght = 0;
                //初始化一个开始打印的index
                int printStart = 0;
                //总长度大于已打印的长度，那么就一直打印
                while (strLength > printLenght) {

                    if ((printStart + LOG_MAXLENGTH) <= strLength) {
                        Log.d(tagName, msg.substring(printStart, printStart + LOG_MAXLENGTH));
                        printLenght = printStart + LOG_MAXLENGTH;
                        printStart = printStart + LOG_MAXLENGTH;
                    } else {
                        Log.d(tagName, msg.substring(printStart, strLength));
                        printLenght = strLength;
                    }
                }

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
            //如果是null
            if (msg == null) {
                Log.i(tagName, "null");
                return;
            }

            //如果是空的话
            if (TextUtils.isEmpty(msg)) {
                Log.i(tagName, "\"\"");
                return;
            }


            int strLength = msg.length();
            if (strLength > LOG_MAXLENGTH) {
                //如果打印长度大于，限制的最大长度后，我们就需要分段打印
                //已经打印的长度
                int printLenght = 0;
                //初始化一个开始打印的index
                int printStart = 0;
                //总长度大于已打印的长度，那么就一直打印
                while (strLength > printLenght) {

                    if ((printStart + LOG_MAXLENGTH) <= strLength) {
                        Log.i(tagName, msg.substring(printStart, printStart + LOG_MAXLENGTH));
                        printLenght = printStart + LOG_MAXLENGTH;
                        printStart = printStart + LOG_MAXLENGTH;
                    } else {
                        Log.i(tagName, msg.substring(printStart, strLength));
                        printLenght = strLength;
                    }
                }

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
            //如果是null
            if (msg == null) {
                Log.w(tagName, "null");
                return;
            }

            //如果是空的话
            if (TextUtils.isEmpty(msg)) {
                Log.w(tagName, "\"\"");
                return;
            }


            int strLength = msg.length();
            if (strLength > LOG_MAXLENGTH) {
                //如果打印长度大于，限制的最大长度后，我们就需要分段打印
                //已经打印的长度
                int printLenght = 0;
                //初始化一个开始打印的index
                int printStart = 0;
                //总长度大于已打印的长度，那么就一直打印
                while (strLength > printLenght) {

                    if ((printStart + LOG_MAXLENGTH) <= strLength) {
                        Log.w(tagName, msg.substring(printStart, printStart + LOG_MAXLENGTH));
                        printLenght = printStart + LOG_MAXLENGTH;
                        printStart = printStart + LOG_MAXLENGTH;
                    } else {
                        Log.w(tagName, msg.substring(printStart, strLength));
                        printLenght = strLength;
                    }
                }

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
            //如果是null
            if (msg == null) {
                Log.e(tagName, "null");
                return;
            }

            //如果是空的话
            if (TextUtils.isEmpty(msg)) {
                Log.e(tagName, "\"\"");
                return;
            }


            int strLength = msg.length();
            if (strLength > LOG_MAXLENGTH) {
                //如果打印长度大于，限制的最大长度后，我们就需要分段打印
                //已经打印的长度
                int printLenght = 0;
                //初始化一个开始打印的index
                int printStart = 0;
                //总长度大于已打印的长度，那么就一直打印
                while (strLength > printLenght) {

                    if ((printStart + LOG_MAXLENGTH) <= strLength) {
                        Log.e(tagName, msg.substring(printStart, printStart + LOG_MAXLENGTH));
                        printLenght = printStart + LOG_MAXLENGTH;
                        printStart = printStart + LOG_MAXLENGTH;
                    } else {
                        Log.e(tagName, msg.substring(printStart, strLength));
                        printLenght = strLength;
                    }
                }

            } else {
                Log.e(tagName, msg);
            }
        }
    }
}
