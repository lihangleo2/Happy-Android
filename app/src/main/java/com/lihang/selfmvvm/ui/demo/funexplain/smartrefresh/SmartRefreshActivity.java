package com.lihang.selfmvvm.ui.demo.funexplain.smartrefresh;

import android.view.View;

import com.leo.utilspro.utils.DataUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.databinding.SmartrefreshActivityBinding;

import java.util.ArrayList;

/**
 * Created by leo
 * on 2021/11/10.
 */
public class SmartRefreshActivity extends BaseActivity<NormalViewModel, SmartrefreshActivityBinding> {
    private SmartAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.smartrefresh_activity;
    }

    @Override
    protected void processLogic() {
        binding.includeEmpty.setImageEmpty(R.mipmap.no_data);
        binding.includeEmpty.setTextEmpty("暂无收藏");
        //
        //
        adapter = new SmartAdapter();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            arrayList.add("");
        }
        adapter.setDataList(arrayList);
        binding.recyclerView.setAdapter(adapter);

    }


    public void getBoxList(boolean isShowDialog) {
            //这里封装了数据填充器，如果pagerNumber == 1的时候，内部会情况数据
//        mViewModel.getCollects(ParamsBuilder.build().isShowDialog(isShowDialog)).observe(this, resource -> {
//            resource.handler(new OnCallback<List<TiktokBean>>() {
//                @Override
//                public void onSuccess(List<TiktokBean> data) {
//                    DataUtils.initData(pageNumber, boxBeans, data, boxListAdapter, binding.smartRefreshLayout, binding.includeEmpty.relativeNull);
//                }
//            });
//        });


    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
