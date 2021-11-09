package com.lihang.selfmvvm.ui.demo.funexplain.bannerintro;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.bean.BannerBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，图片+标题
 */

public class ImageTitleAdapter extends BannerAdapter<BannerBean, ImageTitleHolder> {

    public ImageTitleAdapter(List<BannerBean> mDatas) {
        super(mDatas);
    }

    @Override
    public ImageTitleHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageTitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image_title, parent, false));
    }

    @Override
    public void onBindView(ImageTitleHolder holder, BannerBean data, int position, int size) {
        Glide.with(holder.itemView)
                .load(data.getImagePath())
                .thumbnail(Glide.with(holder.itemView).load(R.mipmap.loading))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);

        holder.title.setText(data.getTitle());
    }

}
