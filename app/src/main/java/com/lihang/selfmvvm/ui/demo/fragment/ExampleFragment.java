package com.lihang.selfmvvm.ui.demo.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.ToastUtils;
import com.leo.utilspro.utils.UIUtils;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.customview.popup.CommonPopupWindow;
import com.lihang.selfmvvm.databinding.FragmentDemoBinding;
import com.lihang.selfmvvm.databinding.FragmentExampleBinding;
import com.lihang.selfmvvm.ui.MainActivity;
import com.lihang.selfmvvm.ui.demo.home.HomeActivity;
import com.lihang.selfmvvm.ui.demo.login.LoginActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCropActivity;

import net.moyokoo.diooto.Diooto;
import net.moyokoo.diooto.config.DiootoConfig;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import me.panpf.sketch.SketchImageView;

import static com.yalantis.ucrop.UCrop.EXTRA_OUTPUT_URI;

/**
 * Created by leo
 * on 2020/10/21.
 * 1、选择图片，剪裁图片；
 * 2、点击图片，小图到大图，拖拽退出。仿微信功能，
 * 都在这个页面展示
 */
public class ExampleFragment extends BaseFragment<NormalViewModel, FragmentExampleBinding> {
    private RxPermissions rxPermissions;
    private ArrayList<ImageItem> Selects = new ArrayList<>();
    private String imagePath;
    int index;

    //退出登录pop
    private CommonPopupWindow popupWindow_share;

    //到时候这里用来传值
    public static ExampleFragment newFragment(int index) {
        ExampleFragment fragment = new ExampleFragment();
        fragment.index = index;
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_example;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        rxPermissions = new RxPermissions(this);
        initPop();
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            ImagePicker imagePicker = ImagePicker.getInstance();
                            imagePicker.setCrop(false);
                            imagePicker.setMultiMode(false);
                            imagePicker.setShowCamera(false);
                            imagePicker.setSelectLimit(3);
                            Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, Selects);
                            startActivityForResult(intent, 100);
                        }
                    } else {
                        ToastUtils.showToast("相册需要此权限");
                    }
                });
                break;

            case R.id.button_crop:
                if (Selects.size() <= 0) {
                    ToastUtils.showToast("请先添加图片~");
                    return;
                }

                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                            ActivitysBuilder.build(ExampleFragment.this, UCropActivity.class)
                                    .putExtra("filePath", Selects.get(0).path)
                                    .startActivityForResult(11);

                        }
                    } else {
                        ToastUtils.showToast("相册需要此权限");
                    }
                });


                break;

            case R.id.image:
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(imagePath);
                int position = 0;
                View[] views = new View[]{v};
                String[] arrUrls = (String[]) arrayList.toArray(new String[arrayList.size()]);
                Diooto diootoOrder = new Diooto(getActivity())
                        .indicatorVisibility(View.VISIBLE)
                        .urls(arrUrls)
                        .type(DiootoConfig.PHOTO)
                        .immersive(true)
                        .position(position, 0)
                        .views(views)
                        .loadPhotoBeforeShowBigImage(new Diooto.OnLoadPhotoBeforeShowBigImageListener() {
                            @Override
                            public void loadView(SketchImageView sketchImageView, int position) {
                                sketchImageView.displayImage(arrUrls[position]);
                            }
                        })
                        .start();
                break;

            case R.id.button_login_out:
                //退出登录
                popupWindow_share.showBottom(binding.getRoot(), 0.5f);
                break;

            case R.id.txt_pop_cancle:
//                点击取消pop
                popupWindow_share.dismiss();
                break;

            case R.id.txt_confirm_logout:
                //确认退出登录
                popupWindow_share.dismiss();
                MyApplication.logOut();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            Selects.clear();
            ArrayList<ImageItem> selects = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            Selects.addAll(selects);
            imagePath = selects.get(0).path;
            Glide.with(binding.image).
                    load(imagePath).
                    placeholder(R.mipmap.ic_launcher).
                    error(R.mipmap.ic_launcher).
//                    transform(new CenterCrop(), new RoundedCorners(UIUtils.dp2px(10))).
        transform(new FitCenter()).
                    into(binding.image);
        } else if (resultCode == -1) {
            if (requestCode == 11) {
                String outPath = data.getStringExtra(EXTRA_OUTPUT_URI);
                imagePath = outPath;
                LogUtils.i("我没有成功返回吗", outPath);
                if (!TextUtils.isEmpty(outPath)) {
                    Glide.with(binding.image).
                            load(imagePath).
                            placeholder(R.mipmap.ic_launcher).
                            error(R.mipmap.ic_launcher).
                            transform(new FitCenter()).
                            into(binding.image);
                }
            }
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
