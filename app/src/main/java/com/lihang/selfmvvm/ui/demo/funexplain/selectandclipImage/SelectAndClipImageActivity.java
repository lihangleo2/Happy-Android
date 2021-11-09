package com.lihang.selfmvvm.ui.demo.funexplain.selectandclipImage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.SelectclipActivityBinding;
import com.lihang.selfmvvm.ui.demo.fragment.detection.ExamplewhtaFragment;
import com.lihang.selfmvvm.ui.demo.set.editor.EditorMessageActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCropActivity;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import static com.lzy.imagepicker.ui.ImageGridActivity.EXTRAS_TAKE_PICKERS;
import static com.yalantis.ucrop.UCrop.EXTRA_OUTPUT_URI;

/**
 * Created by leo
 * on 2021/11/8.
 */
public class SelectAndClipImageActivity extends BaseActivity<NormalViewModel, SelectclipActivityBinding> {
    private RxPermissions rxPermissions;
    private ArrayList<ImageItem> Selects = new ArrayList<>();
    private String imagePath;

    @Override
    protected int getContentViewId() {
        return R.layout.selectclip_activity;
    }

    @Override
    protected void processLogic() {
        rxPermissions = new RxPermissions(this);
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shadowLayout_select_image:
                //选择相册
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    if (aBoolean) {
                        //检查是否有此权限(有些手机用rxPermissions为true时也没有权限)
                        if (ActivityCompat.checkSelfPermission(SelectAndClipImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SelectAndClipImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            ImagePicker imagePicker = ImagePicker.getInstance();
                            imagePicker.setStyle(CropImageView.Style.CIRCLE);//选择剪裁的样式，圆形还是矩形
                            imagePicker.setCrop(true);//是否剪裁图片
                            imagePicker.setMultiMode(false);//是否是多选模式
                            imagePicker.setShowCamera(false);//选择图片时(是否显示相机)
                            imagePicker.setSelectLimit(3);//多选模式下的图片张数
                            Intent intent = new Intent(SelectAndClipImageActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, Selects);//相册选择
                            startActivityForResult(intent, 100);
                        }
                    } else {
                        ToastUtils.showToast("相册需要此权限");
                    }
                });
                break;

            case R.id.shadowLayout_take_image:
                //相机拍照
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (ActivityCompat.checkSelfPermission(SelectAndClipImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SelectAndClipImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            ImagePicker imagePicker = ImagePicker.getInstance();
                            imagePicker.setCrop(false);
                            imagePicker.setStyle(CropImageView.Style.CIRCLE);
                            imagePicker.setMultiMode(false);
                            imagePicker.setShowCamera(false);
                            Intent intent = new Intent(SelectAndClipImageActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, Selects);
                            intent.putExtra(EXTRAS_TAKE_PICKERS, true);//拍照
                            startActivityForResult(intent, 100);
                        }
                    } else {
                        ToastUtils.showToast("相册需要此权限");
                    }
                });
                break;

            case R.id.shadowLayout_ucrop:
                //选择ucrop来剪裁图片
                if (Selects.size() <= 0) {
                    ToastUtils.showToast("请先添加图片~");
                    return;
                }

                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (ActivityCompat.checkSelfPermission(SelectAndClipImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SelectAndClipImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                            ActivitysBuilder.build(SelectAndClipImageActivity.this, UCropActivity.class)
                                    .putExtra("filePath", Selects.get(0).path)
                                    .startActivityForResult(11);
                        }
                    } else {
                        ToastUtils.showToast("相册需要此权限");
                    }
                });
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //imagepicker返回
            Selects.clear();
            ArrayList<ImageItem> selects = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            Selects.addAll(selects);
            imagePath = selects.get(0).path;
            Glide.with(binding.image)
                    .load(imagePath)
//                    .placeholder(R.mipmap.ic_launcher)
//                    .error(R.mipmap.ic_launcher)
                    .transform(new FitCenter())
                    .into(binding.image);
        } else if (resultCode == -1) {
            if (requestCode == 11) {
                //ucrop剪裁返回
                String outPath = data.getStringExtra(EXTRA_OUTPUT_URI);
                imagePath = outPath;
                if (!TextUtils.isEmpty(outPath)) {
                    Glide.with(binding.image)
                            .load(imagePath)
//                            .placeholder(R.mipmap.ic_launcher)
//                            .error(R.mipmap.ic_launcher)
                            .transform(new FitCenter())
                            .into(binding.image);
                }
            }
        }
    }
}
