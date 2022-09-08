package com.lihang.selfmvvm.ui.home.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseViewHolder;
import com.lihang.selfmvvm.bean.HomeBean;
import com.lihang.selfmvvm.databinding.HomeItemListBinding;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by leo
 * on 2019/11/8.
 */
public class HomeAdapter extends BaseAdapter<HomeBean> {
    private View.OnClickListener onClickListener;

    public HomeAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        HomeItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.home_item_list, viewGroup, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        HomeItemListBinding binding = (HomeItemListBinding) baseViewHolder.binding;
        HomeBean itemBean = dataList.get(i);
        binding.txtTitle.setText(itemBean.getTitle());
        if (TextUtils.isEmpty(itemBean.getSuperChapterName())) {
            binding.txtCome.setText("火星");
        } else {
            binding.txtCome.setText(itemBean.getSuperChapterName());
        }
        if (TextUtils.isEmpty(itemBean.getAuthor())) {
            binding.txtAurtor.setText(itemBean.getShareUser());
        } else {
            binding.txtAurtor.setText(itemBean.getAuthor());
        }

        if (itemBean.isCollect()) {
            binding.imageZan.setImageResource(R.mipmap.card_zan_25);
        } else {
            binding.imageZan.setImageResource(R.mipmap.card_zan_1);
        }
        binding.imageZan.setTag(itemBean);
        binding.txtBlogFrom.setText(itemBean.getNiceShareDate());
        binding.imageZan.setOnClickListener(onClickListener);

    }
}
