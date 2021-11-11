package com.lihang.selfmvvm.ui.demo.funexplain.coordinatorlayout;


import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Administrator on 2018/1/19.
 * 这是多fragment的Adapter
 */

public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> homeFocusBeenList;

    public ViewPagerFragmentAdapter(FragmentManager fm, ArrayList<Fragment> homeFocusBeenList) {
        super(fm);
        this.homeFocusBeenList = homeFocusBeenList;
    }

    @Override
    public Fragment getItem(int position) {
        return homeFocusBeenList.get(position);
    }

    @Override
    public int getCount() {
        return homeFocusBeenList != null ? homeFocusBeenList.size() : 0;
    }
}
