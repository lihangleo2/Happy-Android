package com.lihang.selfmvvm.ui.demo.set;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.MoreUtils;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.customview.popup.CommonPopupWindow;
import com.lihang.selfmvvm.databinding.SetActivityBinding;
import com.lihang.selfmvvm.ui.MainActivity;
import com.lihang.selfmvvm.ui.demo.set.aboutus.AboutUsActivity;
import com.lihang.selfmvvm.ui.demo.set.editor.EditorMessageActivity;

/**
 * Created by leo
 * on 2021/9/26.
 */
public class SetActivity extends BaseActivity<NormalViewModel, SetActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.set_activity;
    }

    @Override
    protected void processLogic() {
        binding.txtVersion.setText(MoreUtils.getVersionName(this));
        initPop();
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_version:
                ActivitysBuilder.build(this, AboutUsActivity.class)
                        .startActivity();
                break;

            case R.id.txt_loginOut:
                //退出登录
                popupWindow_loginOut.showBottom(binding.getRoot(), 0.5f);
                break;

            case R.id.txt_pop_cancle:
                popupWindow_loginOut.dismiss();
                break;

            case R.id.txt_confirm_logout:
                //确认退出登录
                popupWindow_loginOut.dismiss();
                MyApplication.logOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.linear_head:
                ActivitysBuilder.build(this, EditorMessageActivity.class)
                        .startActivity();
                break;
        }
    }


    //退出登录pop
    private CommonPopupWindow popupWindow_loginOut;

    public void initPop() {
        View viewShare = LayoutInflater.from(getContext()).inflate(R.layout.pop_login_out, null);
        viewShare.findViewById(R.id.txt_confirm_logout).setOnClickListener(this);
        viewShare.findViewById(R.id.txt_pop_cancle).setOnClickListener(this);
        popupWindow_loginOut = new CommonPopupWindow.Builder(getContext())
                .setView(viewShare)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)//在外不可用手指取消
                .setAnimationStyle(R.style.pop_animation)//设置popWindow的出场动画
                .create();
    }
}
