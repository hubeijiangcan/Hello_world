package com.mitbbs.summary.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;
import com.mitbbs.summary.view.CircleProgressBar;
import com.mitbbs.summary.view.ColorTrackTextView;
import com.mitbbs.summary.view.QQStepView;

/**
 * Created by whh on 2017/8/9.
 */
public class TestViewActivity extends BaseActivity{

    private QQStepView qqStepView;
    private CircleProgressBar circleProgressBar;

    @Override
    protected int attachLayout() {
        return R.layout.activity_view_test;
    }

    @Override
    protected void bindViews() {
        initToolbar("自定义view测试");
        circleViewTest();

    }


    private void circleViewTest(){
        circleProgressBar = (CircleProgressBar) findViewById(R.id.circle_progress);

        ValueAnimator animator = ObjectAnimator.ofFloat(0,100);
        animator.setDuration(5000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                circleProgressBar.setmCurrentProgress((int) value);
            }
        });

    }

    private void qqStepViewTest(){
//        qqStepView = (QQStepView) findViewById(R.id.qq_setp);

        qqStepView.setMaxValue(4000);

        ValueAnimator animator = ObjectAnimator.ofFloat(0,3000);

        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                qqStepView.setCurrentValue((int)value);
            }
        });
    }
}
