package com.lihang.selfmvvm.ui.demo.login;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.KeyBoardUtils;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.MoreUtils;
import com.leo.utilspro.utils.PreferenceUtil;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.base.bean.EventBusBean;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.bean.User;
import com.lihang.selfmvvm.common.JSONS;
import com.lihang.selfmvvm.databinding.ActivityRegistCodeBinding;
import com.lihang.selfmvvm.ui.MainActivity;
import com.lihang.selfmvvm.utils.AppUtils;
import com.lihang.selfmvvm.utils.TimeCount;


import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by leo
 * on 2019/11/26.
 * 填写验证码，这是手机登录的最后一步
 */
public class RegistCodeActivity extends BaseActivity<NormalViewModel, ActivityRegistCodeBinding> {
    private TimeCount timeCount;
    private String phone;
    private String openId;
    private String type;

    public static void startActivity(Activity context, String phone) {
        Intent intent = new Intent(context, RegistCodeActivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_regist_code;
    }

    @Override
    protected void processLogic() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        timeCount = new TimeCount(60000, 1000, binding.textRepet);

        phone = getIntent().getStringExtra("phone");
        openId = getIntent().getStringExtra("openId");
        type = getIntent().getStringExtra("type");
        String str1 = phone.substring(0, 3);
        String str2 = phone.substring(3, 7);
        String str3 = phone.substring(7, 11);
        binding.txtPhone.setText(str1 + " " + str2 + " " + str3);
        LogUtils.i("这个问题是什么", "111111111111111==" + phone);
        getCode();

        Observable.timer(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(aLong -> {
//            binding.includeBoxAnim.getRoot().setVisibility(View.GONE);
            KeyBoardUtils.openKeybord(binding.editCode1);
        });
    }

    private void getCode() {
        timeCount.start();
    }


    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
        binding.editCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s.toString())) {
                    binding.editCode1.clearFocus();
                    LogUtils.i("bugHere", "==========");
                    if (!codeIsOk()) {
                        binding.editCode2.requestFocus();
                        LogUtils.i("bugHere", "11111111");
                    }
                }
            }
        });

        binding.editCode2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (TextUtils.isEmpty(getStringByUI(binding.editCode2))) {
                        binding.editCode2.clearFocus();
                        binding.editCode1.requestFocus();
                        LogUtils.i("bugHere", "222222222");
                    } else {
                        LogUtils.i("bugHere", "33333");
                        binding.editCode2.setText("");
                    }
                }
                return true;
            }
        });
        binding.editCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.i("bugHere", "44444444444");
                if (!TextUtils.isEmpty(s.toString())) {
                    binding.editCode2.clearFocus();
                    if (!codeIsOk()) {
                        binding.editCode3.requestFocus();
                    }
                }
            }
        });


        binding.editCode3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (TextUtils.isEmpty(getStringByUI(binding.editCode3))) {
                        binding.editCode3.clearFocus();
                        binding.editCode2.requestFocus();
                    } else {
                        binding.editCode3.setText("");
                    }
                }
                return true;
            }
        });

        binding.editCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    binding.editCode3.clearFocus();
                    if (!codeIsOk()) {
                        binding.editCode4.requestFocus();
                    }
                }
            }
        });

        binding.editCode4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (TextUtils.isEmpty(getStringByUI(binding.editCode4))) {
                        binding.editCode4.clearFocus();
                        binding.editCode3.requestFocus();
                    } else {
                        binding.editCode4.setText("");
                    }
                }
                return true;
            }
        });

        binding.editCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    binding.editCode4.clearFocus();
                    codeIsOk();
                }
            }
        });
    }

    public boolean codeIsOk() {
        if (!TextUtils.isEmpty(getStringByUI(binding.editCode1)) && !TextUtils.isEmpty(getStringByUI(binding.editCode2))
                && !TextUtils.isEmpty(getStringByUI(binding.editCode3)) && !TextUtils.isEmpty(getStringByUI(binding.editCode4))
        ) {
            KeyBoardUtils.closeKeybord(binding.editCode1);
            String code = getStringByUI(binding.editCode1) + getStringByUI(binding.editCode2) + getStringByUI(binding.editCode3) + getStringByUI(binding.editCode4);
            LogUtils.i("验证码是否是对的", code + "===");
            LogUtils.i("这个问题是什么", "22222==" + phone);

            PreferenceUtil.put("phone", phone);
            User user = new User();
            user.setToken("test");
            MyApplication.updateUser(user);
            EventBus.getDefault().post(new EventBusBean(3));

            ActivitysBuilder.build(RegistCodeActivity.this, MainActivity.class)
                    .finish(true)
                    .startActivity();
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_left_btn:
                finish();
                break;
            case R.id.text_repet:

                getCode();
                break;
        }
    }


//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        LogUtils.i("是否点击了删除键","11111111111111111");
//        if (event.getKeyCode() ==KeyEvent.KEYCODE_DEL){
//        }
//        return super.dispatchKeyEvent(event);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardUtils.isShouldHideInput(v, ev)) {
                binding.editCode1.clearFocus();
                binding.editCode2.clearFocus();
                binding.editCode3.clearFocus();
                binding.editCode4.clearFocus();
                KeyBoardUtils.closeKeybord(binding.editCode1);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


}
