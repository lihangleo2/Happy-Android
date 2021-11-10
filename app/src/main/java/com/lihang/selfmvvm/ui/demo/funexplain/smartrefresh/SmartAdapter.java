package com.lihang.selfmvvm.ui.demo.funexplain.smartrefresh;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseViewHolder;
import com.lihang.selfmvvm.databinding.ItemSmartExampleBinding;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by leo
 * on 2021/11/10.
 */
public class SmartAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        ItemSmartExampleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_smart_example, viewGroup, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }
}
