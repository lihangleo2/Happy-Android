package com.leo.utilspro.utils;

import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.lihang.nbadapter.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo
 * on 2020/10/19.
 * DataUtils 填充数据工具类
 */
public class DataUtils {
    private DataUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    //


    //不带翻页的设置数据
    public static <T extends Object> void initDataNoPager(List<?> arrayList, List<T> dataList, BaseAdapter adapter, View view) {
        if (dataList != null && dataList.size() > 0) {
            ((List<T>) arrayList).addAll(dataList);
        }
        adapter.notifyDataSetChanged();
        if (view != null) {
            isShowEmpty((ArrayList<?>) arrayList, view);
        }
    }

    //带翻页的设置数据
    public static <T extends Object> void initData(int pageNumber, List<?> arrayList, List<T> dataList, BaseAdapter adapter, SmartRefreshLayout smartRefreshLayout) {
        if (pageNumber == 0) {
            arrayList.clear();
        }

        if (dataList != null && dataList.size() > 0) {
            ((List<T>) arrayList).addAll(dataList);
        } else {
            if (smartRefreshLayout != null) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }

        if (pageNumber == 0) {
            adapter.notifyDataSetChanged();
        } else {
            if (arrayList.size() == 0) {
                adapter.notifyDataSetChanged();
            } else {
                if (dataList != null && dataList.size() > 0) {
                    adapter.notifyItemRangeChanged(arrayList.size() - dataList.size(), dataList.size());
                }
            }
        }
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        }
    }


    public static void isShowEmpty(ArrayList<?> arrayList, View view) {
        if (arrayList != null && arrayList.size() > 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
