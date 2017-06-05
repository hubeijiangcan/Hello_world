package com.mitbbs.summary.activity;


import android.view.View;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;
import com.mitbbs.summary.view.SettingItemView;

import java.util.Set;

/**
 * Created by jc on 2017/5/24.
 */
public class SettingActivity extends BaseActivity{

    private SettingItemView siv_isUpdate;
    private SettingItemView siv_setNum;

    @Override
    protected int attachLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void bindViews() {
        siv_isUpdate = (SettingItemView) findViewById(R.id.siv_isUpdate);

        siv_isUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siv_isUpdate.setChecked(!siv_isUpdate.isChecked());
            }
        });


        siv_setNum = (SettingItemView) findViewById(R.id.siv_setNum);

        siv_setNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siv_setNum.setChecked(!siv_setNum.isChecked());
            }
        });
    }
}
