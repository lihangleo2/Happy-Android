package com.lihang.selfmvvm.ui.collect.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseViewHolder;
import com.lihang.selfmvvm.bean.HomeBean;
import com.lihang.selfmvvm.databinding.HomeItemBinding;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by leo
 * on 2019/11/8.
 */
public class CollectAdapter extends BaseAdapter<HomeBean> {
    private View.OnClickListener onClickListener;

    public CollectAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.home_item, viewGroup, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        HomeItemBinding binding = (HomeItemBinding) baseViewHolder.binding;
        HomeBean itemBean = dataList.get(i);
        binding.txtTitle.setText(itemBean.getTitle());
        if (TextUtils.isEmpty(itemBean.getSuperChapterName())) {
            binding.txtCome.setText("玩安卓");
        } else {
            binding.txtCome.setText(itemBean.getSuperChapterName());
        }

        if (!TextUtils.isEmpty(itemBean.getChapterName())) {
            binding.txtAurtor.setText(itemBean.getChapterName());
        }else {
            binding.txtAurtor.setText("鸿洋");
        }

        binding.imageZan.setImageResource(R.mipmap.card_zan_35);
        binding.imageZan.setTag(R.id.image_zan,itemBean);
        binding.imageZan.setTag(R.id.linear_,i);
        binding.txtBlogFrom.setText(itemBean.getNiceDate());
        binding.imageZan.setOnClickListener(onClickListener);

    }
}
