package com.lihang.selfmvvm.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.lihang.selfmvvm.R;


/**
 * Created by lihang on 2017/8/14.
 */

public class ProDialog extends Dialog {
    View.OnClickListener onClickListener;
    ProwebInterface prowebInterface;
    TextView txt_msg;


    public ProDialog(Context context, ProwebInterface prowebInterface, View.OnClickListener onClickListener) {
        this(context, R.style.MyDialogStyleBottom);
        this.onClickListener =onClickListener;
        this.prowebInterface = prowebInterface;
//        setCanceledOnTouchOutside(false);
//        setCancelable(false);
    }


    public ProDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pro);
        txt_msg = findViewById(R.id.txt_msg);


        findViewById(R.id.btn_cancle).setOnClickListener(onClickListener);
        findViewById(R.id.btn_confirm).setOnClickListener(onClickListener);


        String message = getContext().getString(R.string.login_xieyi_qq);
        SpannableString spanText = new SpannableString(message);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getContext().getResources().getColor(R.color.bluef7));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View view) {
//                ToastUtils.showToast("点击了协议书了");
//                ActivitysBuilder.build(LoginActivity.this, WebActivity.class)
//                        .putExtra("url", SystemConst.HTML_YONGHU)
//                        .startActivity();
                prowebInterface.goweb1();
            }
        }, 111, 117, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        spanText.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getContext().getResources().getColor(R.color.bluef7));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View view) {
//                ToastUtils.showToast("点击了协议书了");
//                ActivitysBuilder.build(LoginActivity.this, WebActivity.class)
//                        .putExtra("url", SystemConst.HTML_YONGHU)
//                        .startActivity();
                prowebInterface.goweb2();

            }
        }, 118, 124, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt_msg.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        txt_msg.setText(spanText);
        txt_msg.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
