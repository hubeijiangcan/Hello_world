package com.mitbbs.summary.activity;

import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;
import com.mitbbs.summary.view.SettingItemView;

/**
 * Created by jc on 2017/5/31.
 */
public class SetUp02Activity extends BaseActivity{

    private SettingItemView siv_bind;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected int attachLayout() {
        sp = getSharedPreferences("config",MODE_PRIVATE);
        editor = sp.edit();
        return R.layout.activity_setup02;
    }

    @Override
    protected void bindViews() {
        siv_bind = (SettingItemView) findViewById(R.id.siv_bind);
        boolean isBind = sp.getBoolean("isBind",false);
        if (sp.getBoolean("isSetup",false) && isBind){
            siv_bind.setChecked(true);
        }else {
            siv_bind.setChecked(false);
        }

        siv_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siv_bind.setChecked(!siv_bind.isChecked());
                editor.putBoolean("isBind",siv_bind.isChecked());
//                if (siv_bind.isChecked()){
//                    editor.putString("sim","");
//                }else {
//                    TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
//                    String sim = tm.getSimSerialNumber();
//                    if (TextUtils.isEmpty(sim)){
//                        Toast.makeText(context,"手机没有上卡",Toast.LENGTH_SHORT).show();
//                        siv_bind.setChecked(false);
//                        editor.putBoolean("isbind",false);
//                   }
//                    editor.putString("sim",sim);
//                }

                editor.commit();

            }
        });
    }


    public void next(View view){
        startActivity(SetUp03Activity.class);
        finish();
    }

    public void pre(View view){
        startActivity(SetUp01Activity.class);
        finish();
    }
}
