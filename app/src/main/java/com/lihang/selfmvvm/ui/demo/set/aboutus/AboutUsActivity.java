package com.lihang.selfmvvm.ui.demo.set.aboutus;

import android.view.View;

import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.base.bean.EventBusBean;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.bean.ProgressBean;
import com.lihang.selfmvvm.customview.dialog.UpdateDialog;
import com.lihang.selfmvvm.customview.tost.MyToast;
import com.lihang.selfmvvm.databinding.SetAboutusActivityBinding;
import com.lihang.smartloadview.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by leo
 * on 2021/9/26.
 */
public class AboutUsActivity extends BaseActivity<NormalViewModel, SetAboutusActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.set_aboutus_activity;
    }

    @Override
    protected void processLogic() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_check_update:
                getApks();
                break;
        }
    }

    UpdateDialog updateDialog;

    private void getApks() {
        MyToast.show("可以查看代码，无真实环境无法展示");
//        mViewModel.getApks(UIUtil.getVersionCode(AboutUsActivity.this) + "", ParamsBuilder.build().isShowDialog(true)).observe(this, resource -> {
//            resource.handler(new OnCallback<ApkUpdateBean>() {
//                @Override
//                public void onSuccess(ApkUpdateBean data) {
//                    if (data.isUpdate()) {
//                        String verName = UIUtil.getVersionName(AboutUsActivity.this);
//                        updateDialog = new UpdateDialog(AboutUsActivity.this);
//                        updateDialog.setCancelable(!data.isForce());
//                        updateDialog.setCanceledOnTouchOutside(!data.isForce());
//                        updateDialog.show();
//                        updateDialog.setMessage( data.getVersion(),verName, data.getFilePath());
//                    } else {
//                        ToastUtils.showToast("当前已是最新版本！~");
//                    }
//                }
//            });
//        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onbackEvent(EventBusBean eventBusBean) {
        switch (eventBusBean.getType()) {
            case 99:
                //更新完毕后关掉dialog
                updateDialog.dismiss();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onbackEvent(ProgressBean progressBean) {
        //不断的去更新下载动态
        if (progressBean != null) {
            updateDialog.setTotal(progressBean.getMax());
            updateDialog.setProgress(progressBean.getProgress());
        }
    }
}
