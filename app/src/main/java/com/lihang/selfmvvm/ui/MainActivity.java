package com.lihang.selfmvvm.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.leo.utilspro.utils.ActivitysBuilder;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.MainActivityBinding;
import com.lihang.selfmvvm.ui.demo.fragment.DemoFragment;
import com.lihang.selfmvvm.ui.demo.fragment.detection.ExamplewhtaFragment;
import com.lihang.selfmvvm.ui.demo.fragment.home.HomeFragment;
import com.lihang.selfmvvm.ui.demo.fragment.mine.MineFragment;
import com.lihang.selfmvvm.ui.demo.login.LoginActivity;
import com.lihang.selfmvvm.utils.AppUtils;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by leo
 * on 2020/10/21.
 */
public class MainActivity extends BaseActivity<NormalViewModel, MainActivityBinding> {
    private static final int FRAGMENT_ONE = 0;
    private static final int FRAGMENT_TWO = 1;
    private static final int FRAGMENT_THREE = 2;
    private int index;
    private int currentTabIndex = 0;
    HomeFragment homeFragment;
    ExamplewhtaFragment fragment_one;
    MineFragment mineFragment;

    private RelativeLayout[] mTabs;
    private FragmentManager manager;
    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>();

    @Override
    protected int getContentViewId() {
        return R.layout.main_activity;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //这里是退出登录去登录页
        ActivitysBuilder.build(this, LoginActivity.class)
                .finish(true)
                .startActivity();
    }



    @Override
    protected void processLogic() {
        //        mImmersionBar = ImmersionBar.with(this)
//                .statusBarColor(R.color.yellow)
//                .keyboardEnable(true)
//                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//                .fitsSystemWindows(true);
//                mImmersionBar.init();
        initFragment();
    }

    private void initFragment() {
        manager = getSupportFragmentManager();
        mTabs = new RelativeLayout[3];
        mTabs[0] = binding.relativeTab1;
        mTabs[1] = binding.relativeTab2;
        mTabs[2] = binding.relativeTab3;

        homeFragment = new HomeFragment();
        fragment_one = ExamplewhtaFragment.newFragment(1);
        mineFragment = new MineFragment();

        list_fragment.add(homeFragment);
        list_fragment.add(fragment_one);
        list_fragment.add(mineFragment);
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
                setStatusBackColor(R.color.white,true);
                break;
            case R.id.relative_tab_2:
                switchFragment(R.id.relative_tab_2);
                setStatusBackColor(R.color.white,true);
                break;
            case R.id.relative_tab_3:
                switchFragment(R.id.relative_tab_3);
                setStatusBackColor(R.color.green93,false);
                break;

        }
    }


    public void setStatusBackColor(int colorId,boolean isDarkFont){
        ImmersionBar.with(this)
                //状态栏字体是否黑色
                .statusBarDarkFont(isDarkFont)
                //状态栏的背景色
                .statusBarColor(colorId)
                //状态栏是否沉浸，true则占据位置
                .fitsSystemWindows(true)
                .init();
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //点击返回键，不退出应用程序。直接返回后台
            ActivitysBuilder.startHome(MainActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
