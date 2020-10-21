package com.leo.utilspro.utils;

/**
 * Created by lihang on 2017/9/13.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.leo.utilspro.utils.abase.LeoUtils;

import androidx.annotation.NonNull;

/**
 * 打开或关闭软键盘
 *
 * @author zhy
 */
public class KeyBoardUtils {

    private KeyBoardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 打开软键盘
     */
    public static void openKeybord() {
        InputMethodManager imm = (InputMethodManager) LeoUtils.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //带EditText参数时，获取焦点
    public static void openKeybord(EditText mEditText) {
        //设置可获得焦点
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        //请求获得焦点
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) LeoUtils.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /**
     * 关闭软键盘
     */

    //如果是EditText的话，同时取消焦点
    public static void closeKeybord(View view) {
        if (view instanceof EditText){
            ((EditText)view).clearFocus();
        }
        InputMethodManager imm =
                (InputMethodManager) LeoUtils.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //只是单纯的关闭软键盘
    public static void closeKeybord(Activity activity) {
        Window window = activity.getWindow();
        View view = window.getCurrentFocus();
        if (view == null) {
            View decorView = window.getDecorView();
            View focusView = decorView.findViewWithTag("keyboardTagView");
            if (focusView == null) {
                view = new EditText(window.getContext());
                view.setTag("keyboardTagView");
                ((ViewGroup) decorView).addView(view, 0, 0);
            } else {
                view = focusView;
            }
            view.requestFocus();
        }
        closeKeybord(view);
    }



    /**
     * 判断当前点击屏幕的地方是否是软键盘：
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //点击屏幕空白处，隐藏软键盘
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//                KeyBoardUtils.closeKeybord(binding.editPhone, LoginActivity.this);
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

}
