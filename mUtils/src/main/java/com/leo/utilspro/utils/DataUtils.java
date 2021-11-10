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


    //不带翻页的设置数据
    public static <T extends Object> void initDataNoPager(List<?> arrayList, List<T> dataList, BaseAdapter adapter) {
        if (dataList != null && dataList.size() > 0) {
            ((List<T>) arrayList).addAll(dataList);
        }
        adapter.notifyDataSetChanged();
    }


    public static <T extends Object> void initDataNoPager(List<?> arrayList, List<T> dataList, BaseAdapter adapter, View emptyView) {
        initDataNoPager(arrayList, dataList, adapter);
        if (emptyView != null) {
            isShowEmpty((ArrayList<?>) arrayList, emptyView);
        }
    }


    public static <T extends Object> void initData(int pageNumber, List<?> arrayList, List<T> dataList, BaseAdapter adapter, SmartRefreshLayout smartRefreshLayout, View emptyView) {
        initData(pageNumber, arrayList, dataList, adapter, smartRefreshLayout);
        if (emptyView != null) {
            isShowEmpty((ArrayList<?>) arrayList, emptyView);
        }
    }

    //带翻页的设置数据
    public static <T extends Object> void initData(int pageNumber, List<?> arrayList, List<T> dataList, BaseAdapter adapter, SmartRefreshLayout smartRefreshLayout) {
        if (pageNumber == 1) {
            arrayList.clear();
        }

        if (dataList != null && dataList.size() > 0) {
            ((List<T>) arrayList).addAll(dataList);
        } else {
            if (smartRefreshLayout != null) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }

        if (pageNumber == 1) {
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


    public static void notifyItemRemoved(int removePosition, BaseAdapter adapter, ArrayList<?> arrayList, View emptyView) {
        notifyItemRemoved(removePosition, adapter, arrayList);
        if (emptyView != null) {
            isShowEmpty(arrayList, emptyView);
        }
    }


    public static void notifyItemRemoved(int removePosition, BaseAdapter adapter, ArrayList<?> arrayList) {
        adapter.notifyItemRemoved(removePosition);
        adapter.notifyItemRangeChanged(removePosition, adapter.getItemCount());
        arrayList.remove(removePosition);
    }


    public static void isShowEmpty(ArrayList<?> arrayList, View view) {
        if (arrayList != null && arrayList.size() > 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
