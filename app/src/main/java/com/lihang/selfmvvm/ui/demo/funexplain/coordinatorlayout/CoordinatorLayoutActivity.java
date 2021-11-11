package com.lihang.selfmvvm.ui.demo.funexplain.coordinatorlayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.leo.utilspro.utils.LogUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.CoordinatorlayoutActivityBinding;
import com.lihang.selfmvvm.ui.demo.funexplain.smartrefresh.SmartAdapter;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

/**
 * Created by leo
 * on 2021/11/11.
 */
public class CoordinatorLayoutActivity extends BaseActivity<NormalViewModel, CoordinatorlayoutActivityBinding> implements AppBarLayout.OnOffsetChangedListener {
    private ViewPagerFragmentAdapter adapter;
    private ArrayList<String> mDataList = new ArrayList<>();
    private CommonNavigator commonNavigator;

    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.coordinatorlayout_activity;
    }

    @Override
    protected void processLogic() {
        ImmersionBar.with(this).init();
        initViewPager();
        initMagicIndicator();
    }


    @Override
    protected void setListener() {
        binding.appBar.addOnOffsetChangedListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    private void initViewPager() {

        CoordinDemoFragment coordinDemoFragment1 = new CoordinDemoFragment();
        CoordinDemoFragment coordinDemoFragment2 = new CoordinDemoFragment();
        CoordinDemoFragment coordinDemoFragment3 = new CoordinDemoFragment();
        fragments.add(coordinDemoFragment1);
        fragments.add(coordinDemoFragment2);
        fragments.add(coordinDemoFragment3);

        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments);
        binding.viewPager.setAdapter(adapter);
    }


    private void initMagicIndicator() {
        mDataList.add("作品");
        mDataList.add("喜欢");
        mDataList.add("收藏");

        binding.magicIndicator.setBackgroundColor(getResources().getColor(R.color.transparent));
        commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setRightPadding((int) getResources().getDimension(R.dimen.dp_6));
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                final BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.grey_99));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.black1b));
//                simplePagerTitleView.setTextSize( getResources().getDimensionPixelSize(R.dimen.sp_16));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimensionPixelSize(R.dimen.sp_16));
//                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.viewPager.setCurrentItem(index);
                    }
                });

                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
                return badgePagerTitleView;


            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(getResources().getColor(R.color.blue4b));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(getResources().getDimensionPixelSize(R.dimen.sp_16) * 3 / 2);
                return indicator;
            }
        });
        binding.magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.magicIndicator, binding.viewPager);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (Math.abs(i) > binding.appBar.getTotalScrollRange() / 3) {
            if (binding.toolbar.getVisibility() == View.INVISIBLE) {
                ImmersionBar.with(this).statusBarDarkFont(true).init();
                binding.toolbar.setVisibility(View.VISIBLE);

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentAlpha = (float) animation.getAnimatedValue();
                        binding.toolbar.setAlpha(currentAlpha);
                    }
                });
                valueAnimator.start();

            }
        } else {
            if (binding.toolbar.getVisibility() == View.VISIBLE) {
                ImmersionBar.with(this).statusBarDarkFont(false).init();
                binding.toolbar.setVisibility(View.INVISIBLE);
            }
        }
    }
}
