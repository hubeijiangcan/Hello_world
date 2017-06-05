package com.mitbbs.summary.activity;

import android.content.SharedPreferences;
import android.view.View;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;

/**
 * Created by jc on 2017/5/31.
 */
public class SetUp04Activity extends BaseActivity {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected int attachLayout() {
        sp = getSharedPreferences("config",MODE_PRIVATE);
        editor = sp.edit();
        return R.layout.activity_setup04;
    }

    @Override
    protected void bindViews() {

    }
    public void next(View view){
        editor.putBoolean("isSetup",true).commit();
        startActivity(LostFindActivity.class);
        finish();
    }

    public void pre(View view){
        startActivity(SetUp03Activity.class);
        finish();
    }
}
