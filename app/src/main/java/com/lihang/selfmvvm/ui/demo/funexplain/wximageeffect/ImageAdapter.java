package com.lihang.selfmvvm.ui.demo.funexplain.wximageeffect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseViewHolder;
import com.lihang.selfmvvm.databinding.ItemGridBinding;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import me.panpf.sketch.Sketch;
import me.panpf.sketch.request.DisplayOptions;

/**
 * Created by leo
 * on 2021/11/8.
 */
public class ImageAdapter extends BaseAdapter<String> {
    View.OnClickListener onClickListener;

    public ImageAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        ItemGridBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_grid, viewGroup, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        ItemGridBinding binding = (ItemGridBinding) baseViewHolder.binding;
        String itemBen = dataList.get(i);

        DisplayOptions displayOptions = new DisplayOptions();
        displayOptions.setLoadingImage(R.mipmap.ic_launcher_leo);
        displayOptions.setErrorImage(R.mipmap.image_error);
//        holder1.srcImageView.setShowGifFlagEnabled(R.mipmap.ic_gif);
        Sketch.with(binding.srcImageView.getContext()).display(itemBen, binding.srcImageView)
                .options(displayOptions)
                .commit();
        binding.srcImageView.setOnClickListener(onClickListener);
        binding.srcImageView.setTag(i);
    }
}
