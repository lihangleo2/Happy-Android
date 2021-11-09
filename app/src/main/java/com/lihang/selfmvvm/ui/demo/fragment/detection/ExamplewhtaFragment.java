package com.lihang.selfmvvm.ui.demo.fragment.detection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.customview.popup.CommonPopupWindow;
import com.lihang.selfmvvm.databinding.FragmentExampleBinding;
import com.lihang.selfmvvm.ui.MainActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.bannerintro.BannerActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.edittext.EditTextViewActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.glide.GlideUserActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.network.NetWorkExplainActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.pickviewintro.PickerViewActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.selectandclipImage.SelectAndClipImageActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.wximageeffect.LikeWxImageEffectActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCropActivity;

import net.moyokoo.diooto.Diooto;
import net.moyokoo.diooto.config.DiootoConfig;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import me.panpf.sketch.SketchImageView;

import static com.yalantis.ucrop.UCrop.EXTRA_OUTPUT_URI;

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

            case R.id.shadowLayout_edittext:
                //【editTextView的疑难杂症】文档和使用
                ActivitysBuilder.build(this, EditTextViewActivity.class)
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
