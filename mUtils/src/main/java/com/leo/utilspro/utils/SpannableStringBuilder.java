package com.leo.utilspro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.leo.utilspro.R;
import com.leo.utilspro.utils.abase.LeoUtils;

/**
 * Created by leo
 * on 2020/10/22.
 */
public class SpannableStringBuilder {
    private String sourceStr;
    SpannableString spannableString;

    public SpannableStringBuilder(String sourceStr) {
        this.sourceStr = sourceStr;
        spannableString = new SpannableString(sourceStr);

    }

    public static SpannableStringBuilder build(String sourceStr) {
        return new SpannableStringBuilder(sourceStr);
    }


    // 设置字体颜色值
    public SpannableStringBuilder color(int color, int start, int end) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    // 设置字体颜色值
    public SpannableStringBuilder color(int color, String target) {
        if (sourceStr.contains(target)) {
            int start = sourceStr.indexOf(target);
            int end = start + target.length();
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
            spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    //设置字体背景颜色值
    public SpannableStringBuilder backgroundColor(int color, int start, int end) {
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(color);
        spannableString.setSpan(backgroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置字体背景颜色值
    public SpannableStringBuilder backgroundColor(int color, String target) {
        if (sourceStr.contains(target)) {
            int start = sourceStr.indexOf(target);
            int end = start + target.length();
            BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(color);
            spannableString.setSpan(backgroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    // 设置字体下划线
    public SpannableStringBuilder underline(int start, int end) {
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    // 设置字体下划线
    public SpannableStringBuilder underline(String target) {
        if (sourceStr.contains(target)) {
            int start = sourceStr.indexOf(target);
            int end = start + target.length();
            UnderlineSpan underlineSpan = new UnderlineSpan();
            spannableString.setSpan(underlineSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    // 设置字体删除线
    public SpannableStringBuilder deleteline(int start, int end) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    // 设置字体删除线
    public SpannableStringBuilder deleteline(String target) {
        if (sourceStr.contains(target)) {
            int start = sourceStr.indexOf(target);
            int end = start + target.length();
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    // 设置字体大小，textSize
    public SpannableStringBuilder textSize(int dimens, int start, int end) {
        int size = (int) LeoUtils.getApplication().getResources().getDimension(dimens);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(size);
        spannableString.setSpan(absoluteSizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    // 设置字体大小，textSize
    public SpannableStringBuilder textSize(int dimens, String target) {
        if (sourceStr.contains(target)) {
            int size = (int) LeoUtils.getApplication().getResources().getDimension(dimens);
            int start = sourceStr.indexOf(target);
            int end = start + target.length();
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(size);
            spannableString.setSpan(absoluteSizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    // 设置字体大小，RelativeSizeSpan 在原有基础上去修改
    public SpannableStringBuilder textSize(float scale, int start, int end) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(scale);
        spannableString.setSpan(relativeSizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    // 设置字体大小，RelativeSizeSpan 在原有基础上去修改
    public SpannableStringBuilder textSize(float scale, String target) {
        if (sourceStr.contains(target)) {
            int start = sourceStr.indexOf(target);
            int end = start + target.length();
            RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(scale);
            spannableString.setSpan(relativeSizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    // 设置字体加粗
    public SpannableStringBuilder bold(int start, int end) {
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(styleSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    // 设置字体加粗
    public SpannableStringBuilder bold(String target) {
        if (sourceStr.contains(target)) {
            int start = sourceStr.indexOf(target);
            int end = start + target.length();
            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }


    // 添加图片  注意插入图片，会覆盖文案。所以在插入图片前得有个占位
    public SpannableStringBuilder image(int start, int end, int drawableId, int dimensWith, int dimensHeight) {
        Drawable drawable = LeoUtils.getApplication().getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, (int) LeoUtils.getApplication().getResources().getDimension(dimensWith), (int) LeoUtils.getApplication().getResources().getDimension(dimensHeight));
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    //添加点击事件
    public SpannableStringBuilder clickSpan(int start, int end, int color, boolean isUnderline, ClickableSpanListener clickableSpanListener) {
        this.clickableSpanListener = clickableSpanListener;
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(LeoUtils.getApplication().getResources().getColor(color));       //设置文件颜色
                ds.setUnderlineText(isUnderline);      //设置下划线
            }

            @Override
            public void onClick(View view) {
                if (clickableSpanListener != null) {
                    clickableSpanListener.onClick(view);
                }
            }
        }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return this;
    }


    //添加点击事件
    public SpannableStringBuilder clickSpan(String target, int color, boolean isUnderline, ClickableSpanListener clickableSpanListener) {
        if (sourceStr.contains(target)) {
            int start = sourceStr.indexOf(target);
            int end = start + target.length();

            this.clickableSpanListener = clickableSpanListener;
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(color);       //设置文件颜色
                    ds.setUnderlineText(isUnderline);      //设置下划线
                }

                @Override
                public void onClick(View view) {
                    if (clickableSpanListener != null) {
                        clickableSpanListener.onClick(view);
                    }
                }
            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return this;
    }


    private ClickableSpanListener clickableSpanListener;

    public void into(TextView textView) {
        textView.setText(spannableString);
        if (clickableSpanListener != null) {
            textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
            textView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
        }
    }

}
