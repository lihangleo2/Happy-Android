package com.lihang.selfmvvm.ui.login;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.bean.User;
import com.lihang.selfmvvm.bean.basebean.EventBusBean;
import com.lihang.selfmvvm.bean.basebean.ParamsBuilder;
import com.lihang.selfmvvm.common.PARAMS;
import com.lihang.selfmvvm.databinding.ActivityLoginBinding;
import com.lihang.selfmvvm.utils.ActivityUtils;
import com.lihang.selfmvvm.utils.KeyBoardUtils;
import com.lihang.selfmvvm.utils.PreferenceUtil;
import com.lihang.selfmvvm.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import static com.lihang.selfmvvm.utils.KeyBoardUtils.isShouldHideInput;

/**
 * Created by leo
 * on 2019/11/13.
 */
public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> implements TextWatcher {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (binding.editPhone == null) {
                return;
            }
            switch (msg.what) {
                case 11:
                    KeyBoardUtils.openKeybord(binding.editPhone, LoginActivity.this);
                    break;

                case 12:
                    String text_phone = getStringByUI(binding.editPhone);
                    String text_password = getStringByUI(binding.editPassworld);
                    mViewModel.login(PARAMS.login(text_phone, text_password), ParamsBuilder.build().isShowDialog(false))
                            .observe(LoginActivity.this, resource -> {
                                resource.handler(new OnCallback<User>() {
                                    @Override
                                    public void onSuccess(User data) {
                                        MyApplication.updateUser(data);
                                        PreferenceUtil.put("userName", getStringByUI(binding.editPhone));
                                        //联网成功：通过设置监听AnimatorListener即是启动 小圆扩散全屏动画。在此动画全部完成后拿到回调onAnimtionEnd
                                        binding.smartLoadingView.loginSuccess(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                ActivityUtils.finishWithAnim(LoginActivity.this, R.anim.scale_test_home, R.anim.scale_test2);
                                                EventBus.getDefault().post(new EventBusBean(1));
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(String msg) {
                                        binding.smartLoadingView.netFaile(msg);
                                    }

                                    @Override
                                    public void onCompleted() {
                                        super.onCompleted();
                                        binding.editPhone.setFocusable(true);
                                        binding.editPhone.setFocusableInTouchMode(true);
                                        binding.editPassworld.setFocusable(true);
                                        binding.editPassworld.setFocusableInTouchMode(true);
                                    }
                                });
                            });
                    break;
            }
        }
    };

    @Override
    protected void processLogic() {
        binding.smartLoadingView.cannotClick();
        initXiey();
        openKeyBord();

    }

    private void openKeyBord() {
        String userName = (String) PreferenceUtil.get("userName", "");
        if (!TextUtils.isEmpty(userName)) {
            binding.editPhone.setText(userName);
            binding.editPassworld.requestFocus();
        } else {
            binding.editPhone.requestFocus();
        }

        mHandler.sendEmptyMessageDelayed(11, 500);
    }


    @Override
    protected void setListener() {
        binding.smartLoadingView.setLoginClickListener(() -> {
            if (TextUtils.isEmpty(getStringByUI(binding.editPhone))) {
                ToastUtils.showToast("账号不能为空~");
                return;
            }

            if (TextUtils.isEmpty(getStringByUI(binding.editPassworld))) {
                ToastUtils.showToast("密码不能为空");
                return;
            }
            binding.editPhone.setFocusable(false);
            binding.editPassworld.setFocusable(false);
            mHandler.sendEmptyMessageDelayed(12, 600);
        });
        binding.editPhone.addTextChangedListener(this);
        binding.editPassworld.addTextChangedListener(this);
        binding.leoTitleBar.bar_left_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_left_btn:
                finish();
                break;
        }
    }


    private void initXiey() {
        /**
         * 协议
         * */
        SpannableString spanText = new SpannableString(getString(R.string.login_xieyi));
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.homeyellow));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View view) {
                ToastUtils.showToast("点击了协议书了");

            }
        }, 10, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textProtrol.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        binding.textProtrol.setText(spanText);
        binding.textProtrol.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                binding.editPhone.clearFocus();
                binding.editPassworld.clearFocus();
                KeyBoardUtils.closeKeybord(binding.editPhone, LoginActivity.this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(getStringByUI(binding.editPhone)) && !TextUtils.isEmpty(getStringByUI(binding.editPassworld))) {
            binding.smartLoadingView.reset();
        } else {
            binding.smartLoadingView.cannotClick();
        }
    }
}
