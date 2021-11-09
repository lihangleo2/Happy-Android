package com.lihang.selfmvvm.ui.demo.funexplain.wximageeffect;

import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.LiekwxEffectActivityBinding;

import net.moyokoo.diooto.Diooto;
import net.moyokoo.diooto.config.DiootoConfig;

import java.util.ArrayList;

import me.panpf.sketch.SketchImageView;

/**
 * Created by leo
 * on 2021/11/8.
 */
public class LikeWxImageEffectActivity extends BaseActivity<NormalViewModel, LiekwxEffectActivityBinding> {
    private ArrayList<String> sourceImages = new ArrayList<>();
    private ImageAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.liekwx_effect_activity;
    }

    @Override
    protected void processLogic() {
        initImages();
    }

    private void initImages() {
        sourceImages.add("https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/54a4b40807034b3090708c935689345f~tplv-k3u1fbpfcp-zoom-crop-mark:1304:1304:1304:734.awebp?");
        sourceImages.add("https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/72965abf66484d08aadf3e60922312f6~tplv-k3u1fbpfcp-no-mark:720:720:720:480.awebp?");
        sourceImages.add("https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6398b2d947ba4afeafab1ba50b3d61de~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp");
        sourceImages.add("https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/85d4090a5c6c401c8b3e81131582aaa6~tplv-k3u1fbpfcp-no-mark:720:720:720:480.awebp?");
        sourceImages.add("https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bb386cb2f80344daab3984014b2a5441~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?");
        sourceImages.add("https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/784155e57838473fa12a3746fb92e294~tplv-k3u1fbpfcp-no-mark:720:720:720:480.awebp?");
        sourceImages.add("https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/gold-user-assets/2019/2/2/168ad467bb363cdc~tplv-t2oaga2asx-no-mark:240:240:240:160.awebp");
        sourceImages.add("https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/a23a467270f8448184b3ab952484de12~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?");
        sourceImages.add("https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3b21c999cbbc482a9a937dd84b8732d2~tplv-k3u1fbpfcp-no-mark:240:240:240:160.awebp?");

        adapter = new ImageAdapter(this);
        adapter.setDataList(sourceImages);
        adapter.setGridDivide(binding.recyclerView, (int) getResources().getDimension(R.dimen.dp_5));
        binding.recyclerView.setAdapter(adapter);

        Glide.with(binding.image1)
                .load(sourceImages.get(0))
//                    .placeholder(R.mipmap.ic_launcher)
//                    .error(R.mipmap.ic_launcher)
                .transform(new CenterCrop())
                .into(binding.image1);
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_1:
                //单个view的效果
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(sourceImages.get(0));
                int position = 0;
                View[] views = new View[]{v};
                String[] arrUrls = (String[]) arrayList.toArray(new String[arrayList.size()]);
                Diooto diootoOrder = new Diooto(LikeWxImageEffectActivity.this)
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

            case R.id.srcImageView:
                int position_re = (int) v.getTag();

                String[] array = (String[]) sourceImages.toArray(new String[sourceImages.size()]);
                Diooto diooto = new Diooto(this)
                        .indicatorVisibility(View.VISIBLE)
                        .urls(array)
                        .type(DiootoConfig.PHOTO)
                        .immersive(true)
                        .position(position_re, 0)
                        .views(binding.recyclerView, R.id.srcImageView)
                        .loadPhotoBeforeShowBigImage(new Diooto.OnLoadPhotoBeforeShowBigImageListener() {
                            @Override
                            public void loadView(SketchImageView sketchImageView, int position) {
                                sketchImageView.displayImage(sourceImages.get(position));
                                sketchImageView.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        ToastUtils.showToast("Long click");
                                        return false;
                                    }
                                });
                            }
                        }).start();
                break;
        }
    }
}
