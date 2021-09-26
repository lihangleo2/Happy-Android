package com.leo.utilspro.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by leo
 * on 2020/10/19.
 */
public class TextViewUtils {
    private TextViewUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 固定宽度情况下，自适应文本字体大小
     *
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




    /**
     * 设置textView结尾...后面显示的文字和颜色
     * 例：xxxxxxxx...阅读原文
     *
     * @param context    上下文
     * @param textView   textview
     * @param minLines   最少的行数
     * @param originText 原文本
     * @param endText    结尾文字
     * @param endColorID 结尾文字颜色id
     * @param isExpand   当前是否是展开状态
     */
    public static void toggleEllipsize(final Context context,
                                       final TextView textView,
                                       final int minLines,
                                       final String originText,
                                       final String endText,
                                       final int endColorID,
                                       final boolean isExpand) {
        if (TextUtils.isEmpty(originText)) {
            return;
        }

        if (isExpand) {
            textView.setText(originText);
        } else {
            int paddingLeft = textView.getPaddingLeft();
            int paddingRight = textView.getPaddingRight();
            TextPaint paint = textView.getPaint();
            float moreText = textView.getTextSize() * endText.length();
            float availableTextWidth = (UIUtils.getScreenWidth() - paddingLeft - paddingRight) *
                    minLines - moreText;
            CharSequence ellipsizeStr = TextUtils.ellipsize(originText, paint,
                    availableTextWidth, TextUtils.TruncateAt.END);
            if (ellipsizeStr.length() < originText.length()) {
                CharSequence temp = ellipsizeStr + endText;
                SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
                ssb.setSpan(new ForegroundColorSpan(context.getResources().getColor
                                (endColorID)),
                        temp.length() - endText.length(), temp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                textView.setText(ssb);
            } else {
                textView.setText(originText);
            }
        }
    }



    //字体带下阴影的办法
//    android:shadowColor="#ff009473"
//    android:shadowDx="0"
//    android:shadowDy="8"
//    android:shadowRadius="3.0"

}
