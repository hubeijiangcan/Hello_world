package com.mitbbs.summary.activity;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;
import com.mitbbs.summary.fragmen.ItemFragmen;
import com.mitbbs.summary.view.ColorTrackTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jc on 2017/8/11.
 */
public class ColorChangeTestActivity extends BaseActivity{
    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private LinearLayout mIndicatorContainer;// 变成通用的
    private List<ColorTrackTextView> mIndicators;
    private ViewPager mViewPager;
    @Override
    protected int attachLayout() {
        return R.layout.activity_colorchange;
    }

    @Override
    protected void bindViews() {
        initToolbar("测试使用");
        mIndicators = new ArrayList<>();
        mIndicatorContainer = (LinearLayout) findViewById(R.id.ll_container);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        initIndicator();
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragmen.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 1.左边  位置 position
                ColorTrackTextView left = mIndicators.get(position);
                left.setDistant(ColorTrackTextView.Distant.RIGHT_TO_LEFT);
                left.setCurrentPross(1-positionOffset);

                //右边
                try {
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDistant(ColorTrackTextView.Distant.LEFT_TO_RIGHT);
                    right.setCurrentPross(positionOffset);
                }catch (Exception e){

                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            // 设置颜色
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            // 把新的加入LinearLayout容器
            mIndicatorContainer.addView(colorTrackTextView);
            // 加入集合
            mIndicators.add(colorTrackTextView);
        }
    }
}
