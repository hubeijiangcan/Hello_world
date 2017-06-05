package com.mitbbs.summary.activity;

import android.view.View;
import android.widget.RelativeLayout;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;

/**
 * Created by jc on 2017/5/31.
 */
public class LostFindActivity extends BaseActivity{

    private RelativeLayout rl_reset;

    @Override
    protected int attachLayout() {
        return R.layout.activity_lostfind;
    }

    @Override
    protected void bindViews() {
        rl_reset = (RelativeLayout) findViewById(R.id.rl_reset);

        setLisenter();
    }

    private void setLisenter() {
        rl_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SetUp01Activity.class);
                finish();
            }
        });
    }
}
