package com.mitbbs.summary.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;
import com.mitbbs.summary.view.ColorTrackTextView;
import com.mitbbs.summary.view.QQStepView;

/**
 * Created by whh on 2017/8/9.
 */
public class TestViewActivity extends BaseActivity{

    private QQStepView qqStepView;

    @Override
    protected int attachLayout() {
        return R.layout.activity_view_test;
    }

    @Override
    protected void bindViews() {
        initToolbar("自定义view测试");

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
