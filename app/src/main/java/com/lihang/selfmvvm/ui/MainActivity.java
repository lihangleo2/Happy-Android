package com.lihang.selfmvvm.ui;

import android.view.View;
import android.widget.RelativeLayout;

import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.ActivityMainBinding;
import com.lihang.selfmvvm.ui.demo.fragment.DemoFragment;
import com.lihang.selfmvvm.ui.demo.fragment.ExampleFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by leo
 * on 2020/10/21.
 */
public class MainActivity extends BaseActivity<NormalViewModel, ActivityMainBinding> {
    private static final int FRAGMENT_ONE = 0;
    private static final int FRAGMENT_TWO = 1;
    private static final int FRAGMENT_THREE = 2;
    private int index;
    private int currentTabIndex = 0;
    ExampleFragment fragment_one;
    DemoFragment fragment_two;
    DemoFragment fragment_three;

    private RelativeLayout[] mTabs;
    private FragmentManager manager;
    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processLogic() {
        initFragment();
    }

    private void initFragment() {
        manager = getSupportFragmentManager();
        mTabs = new RelativeLayout[3];
        mTabs[0] = binding.relativeTab1;
        mTabs[1] = binding.relativeTab2;
        mTabs[2] = binding.relativeTab3;


        fragment_one = ExampleFragment.newFragment(1);
        fragment_two = DemoFragment.newFragment(2);
        fragment_three = DemoFragment.newFragment(3);

        list_fragment.add(fragment_one);
        list_fragment.add(fragment_two);
        list_fragment.add(fragment_three);
        switchFragment(R.id.relative_tab_1);
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_tab_1:
                switchFragment(R.id.relative_tab_1);
                break;
            case R.id.relative_tab_2:
                switchFragment(R.id.relative_tab_2);
                break;
            case R.id.relative_tab_3:
                switchFragment(R.id.relative_tab_3);
                break;

        }
    }


    public void switchFragment(int id) {
        FragmentTransaction ft = manager.beginTransaction();
        //fragment切换带动画
//        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
//                android.R.anim.fade_in, android.R.anim.fade_out);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(id);
        String tag = (String) relativeLayout.getTag();
        Fragment f = manager.findFragmentByTag(tag);
        if (f == null) {
            int num = Integer.parseInt(tag);
            ft.add(R.id.framLayout, list_fragment.get(num), tag);

        }

        for (int i = 0; i < list_fragment.size(); i++) {
            Fragment fragment = list_fragment.get(i);
            if (fragment.getTag() != null) {
                if (fragment.getTag().equals(tag)) {
                    ft.show(fragment);
                } else {
                    ft.hide(fragment);
                }
            }
        }
        ft.commitAllowingStateLoss();
        switch (id) {
            case R.id.relative_tab_1://首页
                index = FRAGMENT_ONE;
                break;
            case R.id.relative_tab_2:
                index = FRAGMENT_TWO;
                break;
            case R.id.relative_tab_3:
                index = FRAGMENT_THREE;
                break;
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
}
