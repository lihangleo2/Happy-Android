package com.lihang.selfmvvm.utils.otherfunction;

import com.lihang.selfmvvm.bean.basebean.ParamsBuilder;
import com.lihang.selfmvvm.customview.CustomProgress;
import com.lihang.selfmvvm.utils.LogUtils;
import com.lihang.selfmvvm.utils.PictureProgressUtil;
import com.lihang.selfmvvm.utils.bitmap.PictureUtil;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by leo
 * on 2020/5/27.
 */
public class Function {
    /**
     * rxjava 图片压缩后上传
     * */
//    public void upLoadPic() {
//        dialog = CustomProgress.show(FastInquiryActivity.this, "", true, null);
//
//        Flowable.just(Selects).subscribeOn(Schedulers.io())
//                .map(new io.reactivex.functions.Function<ArrayList<ImageItem>, ArrayList<File>>() {
//                    @Override
//                    public ArrayList<File> apply(ArrayList<ImageItem> imageItems) throws Exception {
//                        ArrayList<File> arrayList = new ArrayList<>();
//                        for (int i = 0; i < Selects.size(); i++) {
//                            arrayList.add(new File(PictureUtil.compressImage(Selects.get(i).path, 50)));
//                        }
//                        return arrayList;
//                    }
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<ArrayList<File>>() {
//                    @Override
//                    public void accept(ArrayList<File> files) throws Exception {
//                        PictureProgressUtil.initData(files.size());
//                        mViewModel.upLoadPicss("condition", "files", files, ParamsBuilder.build().isShowDialog(false)).observe(FastInquiryActivity.this, resource -> {
//                            resource.handler(new OnCallback<List<PicureBean>>() {
//                                @Override
//                                public void onSuccess(List<PicureBean> data) {
//                                    dialog.setMessage("正在下单..");
//                                    LogUtils.i("我现在看看", "现在照片的size == " + data.size());
//                                    for (int i = 0; i < data.size(); i++) {
//                                        LogUtils.i("我现在看看", data.get(i).getFileUrl());
//                                    }
//
//                                }
//
//                                @Override
//                                public void onProgress(int precent, long total) {
//                                    super.onProgress(precent, total);
//                                    dialog.setMessage("图片上传" + PictureProgressUtil.setCurrentProgress(precent) + "%");
//                                }
//
//                            });
//                        });
//                    }
//                });
//    }
}
