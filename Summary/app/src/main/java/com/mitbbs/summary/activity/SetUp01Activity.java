package com.mitbbs.summary.activity;

import android.view.View;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;

/**
 * Created by jc on 2017/5/26.
 */
public class SetUp01Activity extends BaseActivity{
    @Override
    protected int attachLayout() {
        return R.layout.activity_setup01;
    }

    @Override
    protected void bindViews() {

    }

    public void next(View view){
        startActivity(SetUp02Activity.class);
        finish();
    }
}
