package com.lihang.selfmvvm.ui.demo.funexplain.edittext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseViewHolder;
import com.lihang.selfmvvm.databinding.LayoutOneCommentBinding;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class CommentsAdapter extends BaseAdapter<String> {

    View.OnClickListener onClickListener;

    public CommentsAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutOneCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.layout_one_comment, viewGroup, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        LayoutOneCommentBinding binding = (LayoutOneCommentBinding) baseViewHolder.binding;
        binding.txtCommentOne.setText("我是一条小评论==" + position);


    }


}
