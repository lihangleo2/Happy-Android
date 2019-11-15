package com.lihang.selfmvvm.utils;

import android.os.CountDownTimer;
import android.widget.TextView;


/**
 * Created by leo on 2015/11/30.
 * 倒计时，一般用于短信验证码
 */
public class TimeCount extends CountDownTimer {

    private TextView btn_getCode;

    public TimeCount(long millisInFuture, long countDownInterval, TextView btn_getCode) {
        super(millisInFuture, countDownInterval);
        this.btn_getCode = btn_getCode;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn_getCode.setEnabled(false);
        btn_getCode.setText("已发送" + millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        btn_getCode.setText("重新获取");
        btn_getCode.setEnabled(true);
    }
}
