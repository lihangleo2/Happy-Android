package com.lihang.selfmvvm.customview.tost;

/**
 * Created by Leo on 2018/9/4.
 * 自定义Toast
 */

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;


public class MyToast {
    /**
     * 展示toast==LENGTH_SHORT
     *
     * @param msg
     */

    private static Toast toast = null;
    private static Runnable runnableToast = null;
    private static Handler handlerToast = new Handler();

    public static void show(String msg) {
        show(msg, 2000);
    }

    /**
     * 展示toast==LENGTH_LONG
     *
     * @param msg
     */
    public static void showLong(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    public static void show(String massage, int show_length) {
        if (toast == null) {
            Context context = MyApplication.getContext();
            //使用布局加载器，将编写的toast_layout布局加载进来
            View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            //获取ImageView
            //获取TextView
            TextView textView = (TextView) view.findViewById(R.id.toast_tv);
            //设置显示的内容
            textView.setText(massage);
            toast = new Toast(context);
            //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
            toast.setGravity(Gravity.CENTER, 0, 0);
            //设置显示时间
            toast.setView(view);
            toast.show();
            runnableToast = new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            };
            if (runnableToast != null) {
                handlerToast.removeCallbacks(runnableToast);
            }
            handlerToast.postDelayed(runnableToast, show_length);
        } else {
            if (runnableToast != null) {
                handlerToast.removeCallbacks(runnableToast);
            }
            toast.cancel();
            Context context = MyApplication.getContext();
            //使用布局加载器，将编写的toast_layout布局加载进来
            View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            //获取ImageView
            //获取TextView
            TextView textView = (TextView) view.findViewById(R.id.toast_tv);
            //设置显示的内容
            textView.setText(massage);
            toast = new Toast(context);
            //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
            toast.setGravity(Gravity.CENTER, 0, 0);
            //设置显示时间
            toast.setView(view);
            toast.show();
            handlerToast.postDelayed(runnableToast, show_length);
        }
    }
}

