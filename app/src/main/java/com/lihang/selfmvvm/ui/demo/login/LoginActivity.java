package com.lihang.selfmvvm.ui.demo.login;

import static com.leo.utilspro.utils.KeyBoardUtils.isShouldHideInput;

import android.graphics.Color;
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

import com.gyf.immersionbar.ImmersionBar;
import com.leo.utilspro.utils.KeyBoardUtils;
import com.leo.utilspro.utils.MmkvUtils;
import com.leo.utilspro.utils.MoreUtils;
import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.base.bean.EventBusBean;
import com.lihang.selfmvvm.customview.dialog.ProDialog;
import com.lihang.selfmvvm.customview.dialog.ProwebInterface;
import com.lihang.selfmvvm.databinding.LoginActivityBinding;
import com.lihang.selfmvvm.utils.TimeCount;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by leo
 * on 2019/11/13.
 */
public class LoginActivity extends BaseActivity<NormalViewModel, LoginActivityBinding> implements ProwebInterface {
    private ProDialog proDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.login_activity;
    }

    private String phone;
    private TimeCount timeCount;

    @Override
    protected void processLogic() {
        proDialog = new ProDialog(this, this, this);
        //
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        EventBus.getDefault().register(this);
        String phone = (String) MmkvUtils.get("phone","");

//        binding.ShadowLayoutSelect.setSelected(true);

        if (!TextUtils.isEmpty(phone)) {
            binding.editPhone.setText(phone);
        } else {
            int pro = (int) MmkvUtils.get("isPro",0);
            if (pro == 0) {
                Observable.timer(1200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(aLong -> {
                    proDialog.show();
                });
            } else {
                Observable.timer(1200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(aLong -> {
                    KeyBoardUtils.openKeybord(binding.editPhone);
                });
            }
        }
        initXiey();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
        initEditextListener();
        binding.ShadowLayoutSelect.setOnClickListener(this);
        binding.shadowLayoutNext.setOnClickListener(this);

        binding.editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!TextUtils.isEmpty(getStringByUI(binding.editPhone))) {
                        binding.imgDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                finish();
                break;
            case R.id.btn_confirm:
            case R.id.ShadowLayout_select:
                binding.ShadowLayoutSelect.setSelected(!binding.ShadowLayoutSelect.isSelected());
                if (binding.ShadowLayoutSelect.isSelected() && !TextUtils.isEmpty(binding.editPhone.getText().toString().trim())) {
                    binding.shadowLayoutNext.setClickable(true);
                } else {
                    binding.shadowLayoutNext.setClickable(false);
                }

                MmkvUtils.put("isPro",1);
                proDialog.dismiss();
                break;


            case R.id.img_delete:
                binding.editPhone.setText("");
                break;


            case R.id.shadowLayout_next:
                if (MoreUtils.isPhoneNumber(getStringByUI(binding.editPhone))) {
                    //跳转到验证码的页面
                    RegistCodeActivity.startActivity(this, getStringByUI(binding.editPhone));
                } else {
                    ToastUtils.showToast("手机号不合法~");
                }
                break;
        }
    }


    private void initXiey() {
        /**
         * 协议
         * */

        String message = getString(R.string.login_xieyi);
        SpannableString spanText = new SpannableString(message);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.bluef7));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View view) {
                ToastUtils.showToast("点击了用户协议");
//                ActivitysBuilder.build(LoginActivity.this, WebActivity.class)
//                        .putExtra("url", SystemConst.HTML_YONGHU)
//                        .startActivity();

            }
        }, 10, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        spanText.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.bluef7));       //设置文件颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View view) {
                ToastUtils.showToast("点击了隐私政策");

            }
        }, 16, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.textProtrol.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        binding.textProtrol.setText(spanText);
        binding.textProtrol.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }


    private void initEditextListener() {
        binding.setOnClickListener(this);
        binding.editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    binding.imgDelete.setVisibility(View.GONE);
                    binding.shadowLayoutNext.setClickable(false);
                } else {
                    if (binding.ShadowLayoutSelect.isSelected()) {
                        if (MoreUtils.isPhoneNumber(s.toString())) {
                            binding.shadowLayoutNext.setClickable(true);
                        } else {
                            binding.shadowLayoutNext.setClickable(false);
                            if (s.toString().length() == 11) {
                                ToastUtils.showToast("手机号不合法~");
                            }
                        }
                    }
                    binding.imgDelete.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyBoardUtils.closeKeybord(binding.editPhone);
                binding.imgDelete.setVisibility(View.GONE);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onbackEvent(EventBusBean eventBusBean) {
        switch (eventBusBean.getType()) {
            case 3:
                finish();
                break;
        }
    }


    @Override
    public void goweb1() {
        ToastUtils.showToast("点击了用户协议");
    }

    @Override
    public void goweb2() {
        ToastUtils.showToast("点击了隐私政策");
    }
}
