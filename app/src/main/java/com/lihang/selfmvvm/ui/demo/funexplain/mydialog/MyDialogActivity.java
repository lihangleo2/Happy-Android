package com.lihang.selfmvvm.ui.demo.funexplain.mydialog;

import android.view.View;

import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.customview.dialog.DialogUtil;
import com.lihang.selfmvvm.databinding.MydialogActivityBinding;

/**
 * Created by leo
 * on 2021/12/27.
 */
public class MyDialogActivity extends BaseActivity<NormalViewModel, MydialogActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.mydialog_activity;
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shadowLayout_dialog_doc_two:
                DialogUtil.alertDoctorDialog(this, "温馨提示", "我是2个暗摸摸你的", "确定", "取消", new DialogUtil.DialogAlertListener() {
                    @Override
                    public void yes() {
                        ToastUtils.showToast("点击了");
                    }
                });

                break;

            case R.id.shadowLayout_dialog_doc_one:
                DialogUtil.alertDoctorDialog(this, "温馨提示", "我是2个暗摸摸你的", "确定", new DialogUtil.DialogAlertListener() {
                    @Override
                    public void yes() {
                        ToastUtils.showToast("点击了");
                    }
                });
                break;

            case R.id.shadowLayout_dialog_ios_two:

                DialogUtil.alertIosDialog(this, "我是2个按钮的", "确定", "取消", new DialogUtil.DialogAlertListener() {
                    @Override
                    public void yes() {
                        ToastUtils.showToast("点击了");
                    }
                });

                break;

            case R.id.shadowLayout_dialog_ios_one:

                DialogUtil.alertIosDialog(this, "我是2个按钮的", "确定", new DialogUtil.DialogAlertListener() {
                    @Override
                    public void yes() {
                        ToastUtils.showToast("点击了");
                    }
                });

                break;
        }
    }
}
