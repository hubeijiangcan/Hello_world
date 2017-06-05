package com.mitbbs.summary.activity;

import android.view.View;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;

/**
 * Created by jc on 2017/5/31.
 */
public class SetUp03Activity extends BaseActivity{
    @Override
    protected int attachLayout() {
        return R.layout.activity_setup03;
    }

    @Override
    protected void bindViews() {

    }

    public void next(View view){
        startActivity(SetUp04Activity.class);
        finish();
    }

    public void pre(View view){

        startActivity(SetUp02Activity.class);
        finish();
    }

    public void selectContact(View view){
        startActivity(SelectContactActivity.class);
    }
}
