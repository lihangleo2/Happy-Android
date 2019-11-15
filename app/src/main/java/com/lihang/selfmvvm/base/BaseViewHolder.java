package com.lihang.selfmvvm.base;


import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by leo
 * on 2019/11/8.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
