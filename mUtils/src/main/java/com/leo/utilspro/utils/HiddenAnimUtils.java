package com.leo.utilspro.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by leo
 * Description:显示隐藏布局的属性动画(收起和展开)
 * Date:2017/3/30.
 */
public class HiddenAnimUtils {

    public static void closeAnimate(final View view) {
        if (view.getVisibility() == View.VISIBLE) {
            int origHeight = view.getMeasuredHeight();
            ValueAnimator animator = createDropAnimator(view, origHeight, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = origHeight;
                    view.setLayoutParams(layoutParams);
                }
            });
            animator.start();
        }
    }

    public static void openAnim(View v, int origHeight) {
        // 在View.GONE的情况下，控件还未渲染到画面。无法得到宽高
        // 如果先设置View.VISIBLE的话，虽然可以获取宽高。但是画面可能会抖动一下
        if (v.getVisibility() == View.GONE) {
            v.setVisibility(View.VISIBLE);
            ValueAnimator animator = createDropAnimator(v, 0, origHeight);
            animator.start();
        }
    }


    private static ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

}