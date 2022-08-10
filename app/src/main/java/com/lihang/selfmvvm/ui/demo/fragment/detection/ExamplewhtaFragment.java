package com.lihang.selfmvvm.ui.demo.fragment.detection;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.leo.utilspro.utils.ActivitysBuilder;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.customview.popup.CommonPopupWindow;
import com.lihang.selfmvvm.databinding.FragmentExampleBinding;
import com.lihang.selfmvvm.ui.MainActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.bannerintro.BannerActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.coordinatorlayout.CoordinatorLayoutActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.edittext.EditTextViewActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.expandunfold.ExpandUnfoldActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.glide.GlideUserActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.mydialog.MyDialogActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.network.NetWorkExplainActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.pickviewintro.PickerViewActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.selectandclipImage.SelectAndClipImageActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.shareanim.ShareAnimActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.smartrefresh.SmartRefreshActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.wximageeffect.LikeWxImageEffectActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

/**
 * Created by leo
 * on 2020/10/21.
 */
public class ExamplewhtaFragment extends BaseFragment<NormalViewModel, FragmentExampleBinding> {
    int index;

    //退出登录pop
    private CommonPopupWindow popupWindow_share;

    //到时候这里用来传值
    public static ExamplewhtaFragment newFragment(int index) {
        ExamplewhtaFragment fragment = new ExamplewhtaFragment();
        fragment.index = index;
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_example;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initPop();
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.shadowLayout_network:
                //【网络请求】相关文档和用法
                ActivitysBuilder.build(this, NetWorkExplainActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_image:
                //【选择图片和剪裁图片】文档和用法
                ActivitysBuilder.build(this, SelectAndClipImageActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_likewx:
                //【仿微信小图到大图，拖拽退出】文档和用法
                ActivitysBuilder.build(this, LikeWxImageEffectActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_glide:
                //【Glide加载图片】文档和用法
                ActivitysBuilder.build(this, GlideUserActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_banner:
                //【图片轮播banner】文档和用法
                ActivitysBuilder.build(this, BannerActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_pickerView:
                //【pickerView三级联动】文档和使用
                ActivitysBuilder.build(this, PickerViewActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_smartRefresh:
                //【SmartRefreshLayout】文档和使用
                ActivitysBuilder.build(this, SmartRefreshActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_edittext:
                //【editTextView的疑难杂症】文档和使用
                ActivitysBuilder.build(this, EditTextViewActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_coordinatorLayout:
                //【CoordinatorLayout效果】文档和使用
                ActivitysBuilder.build(this, CoordinatorLayoutActivity.class)
                        .startActivity();
                break;

            case R.id.shadowLayout_share:
                //【共享元素的使用】文档和使用
                // 进入共享元素的页面
                Intent intent_in = new Intent(getActivity(), ShareAnimActivity.class);
                ActivityOptionsCompat activityOptions_in = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), new Pair(binding.shadowLayoutShare, "shareView"));
                ActivityCompat.startActivity(getActivity(), intent_in, activityOptions_in.toBundle());
                break;

            case R.id.shadowLayout_expand_unfold:
                //【属性动画实现展开和收起】文档和使用
                ActivitysBuilder.build(this, ExpandUnfoldActivity.class)
                        .startActivity();
                break;

            //【虚线的使用就在本页面】

            case R.id.shadowLayout_dialog:
                //【dialog的使用】文档和使用
                ActivitysBuilder.build(this, MyDialogActivity.class)
                        .startActivity();
                break;


            case R.id.shadowLayout_loginOut:
                //【退出登录的使用方法】
                popupWindow_share.showBottom(binding.getRoot(), 0.5f);
                break;

            case R.id.txt_pop_cancle:
//                点击取消pop
                popupWindow_share.dismiss();
                break;

            case R.id.txt_confirm_logout:
                //确认退出登录。
                // 1、首先设置MainActivity的启动模式launchMode="singleTask"
                // 2、运行以下代码，会关闭MainActivity之后所有栈内的实例，并触发onNewIntent(Intent intent)方法
                popupWindow_share.dismiss();
                MyApplication.logOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }


    public void initPop() {
        View viewShare = LayoutInflater.from(getContext()).inflate(R.layout.pop_login_out, null);
        viewShare.findViewById(R.id.txt_confirm_logout).setOnClickListener(this);
        viewShare.findViewById(R.id.txt_pop_cancle).setOnClickListener(this);
        popupWindow_share = new CommonPopupWindow.Builder(getContext())
                .setView(viewShare)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)//在外不可用手指取消
                .setAnimationStyle(R.style.pop_animation)//设置popWindow的出场动画
                .create();
    }
}
