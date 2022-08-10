package com.lihang.selfmvvm.ui.demo.set.editor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.PictureProgressUtil;
import com.leo.utilspro.utils.ToastUtils;
import com.leo.utilspro.utils.bitmap.BitmapUtil;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.customview.CustomProgress;
import com.lihang.selfmvvm.customview.popup.CommonPopupWindow;
import com.lihang.selfmvvm.databinding.EditorActivityBinding;
import com.lihang.selfmvvm.utils.RxPermissionsUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.lzy.imagepicker.ui.ImageGridActivity.EXTRAS_TAKE_PICKERS;

/**
 * Created by leo
 * on 2021/9/26.
 */
public class EditorMessageActivity extends BaseActivity<EditorViewModel, EditorActivityBinding> {
    //更换头像
    private CommonPopupWindow popupWindow_share;
    private ArrayList<ImageItem> selects = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.editor_activity;
    }

    @Override
    protected void processLogic() {
        initPop();
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_head:
                //弹出更换头像的窗口
                popupWindow_share.showBottom(binding.getRoot(), 0.5f);
                break;

            case R.id.relative_name:
                ActivitysBuilder.build(this,EditorNameActivity.class)
                        .startActivity();
                break;


            case R.id.txt_carm:
                //点击相机
                RxPermissionsUtils.with(this).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (ActivityCompat.checkSelfPermission(EditorMessageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditorMessageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            ImagePicker imagePicker = ImagePicker.getInstance();
                            imagePicker.setCrop(true);
                            imagePicker.setStyle(CropImageView.Style.CIRCLE);
                            imagePicker.setMultiMode(false);
                            imagePicker.setShowCamera(false);
                            Intent intent = new Intent(EditorMessageActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, selects);
                            intent.putExtra(EXTRAS_TAKE_PICKERS, true);
                            startActivityForResult(intent, 100);
                        }
                    } else {
                        ToastUtils.showToast("相册需要此权限");
                    }
                });

                break;


            case R.id.txt_image:
                //点击相册
                RxPermissionsUtils.with(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (ActivityCompat.checkSelfPermission(EditorMessageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditorMessageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            ImagePicker imagePicker = ImagePicker.getInstance();
                            imagePicker.setCrop(true);
                            imagePicker.setStyle(CropImageView.Style.RECTANGLE);
                            imagePicker.setMultiMode(false);
                            imagePicker.setShowCamera(false);
                            Intent intent = new Intent(EditorMessageActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, selects);
                            startActivityForResult(intent, 100);
                        }
                    } else {
                        ToastUtils.showToast("相册需要此权限");
                    }
                });
                break;
        }
    }


    public void initPop() {
        View viewShare = LayoutInflater.from(getContext()).inflate(R.layout.pop_carm, null);
        viewShare.findViewById(R.id.txt_carm).setOnClickListener(this);
        viewShare.findViewById(R.id.txt_image).setOnClickListener(this);
        viewShare.findViewById(R.id.txt_pop_cancle).setOnClickListener(this);
        popupWindow_share = new CommonPopupWindow.Builder(getContext())
                .setView(viewShare)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)//在外不可用手指取消
                .setAnimationStyle(R.style.pop_animation)//设置popWindow的出场动画
                .create();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            selects.clear();
            ArrayList<ImageItem> currentSelect = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            selects.addAll(currentSelect);
            popupWindow_share.dismiss();
//            upLoadImage();
        }
    }

    //单个图片上传
    private void upLoadImage() {
        File file = new File(selects.get(0).path);
        mViewModel.upLoadFile("file", file, ParamsBuilder.build()).observe(EditorMessageActivity.this, resource -> {
            resource.handler(new OnCallback<String>() {
                @Override
                public void onSuccess(String data) {

                }
            });
        });
    }

    //多张图片上传，监测上传进度
    private void upLoadImages() {
        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i <selects.size() ; i++) {
            files.add(new File(selects.get(i).path));
        }

        //PictureProgressUtil 是用于多张图片进度监听的
        PictureProgressUtil.initData(selects.size());
        //这里的type是额外的一个参数
        mViewModel.upLoadMoreFiles("condition","files",files, ParamsBuilder.build()).observe(EditorMessageActivity.this, resource -> {
            resource.handler(new OnCallback<List<String>>() {
                @Override
                public void onSuccess(List<String> data) {

                }

                @Override
                public void onProgress(int precent, long total) {
                    super.onProgress(precent, total);
//                    dialog.setMessage("图片上传" + PictureProgressUtil.setCurrentProgress(precent) + "%");
                }
            });
        });
    }

    /**
     * 1.多张图片上传(不监听上传进度)
     * 2.且使用rxJava背压方式进行图片压缩
     * */

    private CustomProgress dialogLeo;
    //多个图片上传
    private void upLoadImagesWithNoProgress() {

        if (dialogLeo == null) {
            dialogLeo = CustomProgress.show(EditorMessageActivity.this, "", true, null);
        }

        if (!dialogLeo.isShowing()) {
            dialogLeo.show();
        }

        //
        ArrayList<File> arrayListCompress = new ArrayList<>();
        Flowable.just(selects).subscribeOn(Schedulers.io())
                .map(new Function<ArrayList<ImageItem>, ArrayList<File>>() {
                    @Override
                    public ArrayList<File> apply(ArrayList<ImageItem> imageItems) throws Exception {
                        for (int i = 0; i < selects.size(); i++) {
                            arrayListCompress.add(new File(BitmapUtil.compressBitmapByQuality(selects.get(i).path, 65)));
                        }
                        return arrayListCompress;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<File>>() {
                    @Override
                    public void accept(ArrayList<File> files) throws Exception {
                        mViewModel.upLoadMoreFilesWithNoProgress("files", "files", files, ParamsBuilder.build().isShowDialog(false)).observe(EditorMessageActivity.this, resource -> {
                            resource.handler(new OnCallback<List<String>>() {
                                @Override
                                public void onSuccess(List<String> data) {
                                    //压缩完再去上传
                                    //...
                                    dialogLeo.dismiss();
                                }

                                @Override
                                public void onFailure(int errorCode, String msg) {
                                    super.onFailure(errorCode, msg);
                                    LogUtils.i("到底什么鬼", "=========");
                                    dialogLeo.dismiss();
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    super.onError(throwable);
                                    dialogLeo.dismiss();
                                }
                            });
                        });

                    }
                });
    }

}
