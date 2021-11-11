package com.lihang.selfmvvm.ui.demo.funexplain.coordinatorlayout;

import android.os.Bundle;
import android.view.View;

import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.CoordindemoFragmentBinding;
import com.lihang.selfmvvm.ui.demo.funexplain.smartrefresh.SmartAdapter;

import java.util.ArrayList;

/**
 * Created by leo
 * on 2021/11/11.
 */
public class CoordinDemoFragment extends BaseFragment<NormalViewModel, CoordindemoFragmentBinding> {
    private SmartAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.coordindemo_fragment;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        adapter = new SmartAdapter();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            arrayList.add("");
        }
        adapter.setDataList(arrayList);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
