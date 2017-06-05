package com.mitbbs.summary.activity;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mitbbs.summary.OkHttpManeger;
import com.mitbbs.summary.R;
import com.mitbbs.summary.StaticString;
import com.mitbbs.summary.base.BaseActivity;

import org.json.JSONObject;

/**
 * Created by jc on 2017/4/28.
 */
public class ProtectSplashActivity extends BaseActivity{

    private TextView tv_version;
    private String version;
    private OkHttpManeger okHttpManeger;

    @Override
    protected int attachLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void bindViews() {
//        View view = (RelativeLayout) findViewById(R.id.rl_splash);
//        AlphaAnimation aa = new AlphaAnimation(0.5f,1.0f);
//        aa.setDuration(1500);
//        view.startAnimation(aa);
        tv_version = (TextView) findViewById(R.id.tv_version);
        version = getVersionName();
        tv_version.setText("版本号："+ version);

        checkVersion();
    }


    private String getVersionName() {
        String result = "";
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(this.getPackageName(),0);
            result = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void checkVersion(){
//        if (okHttpManeger == null){
//            okHttpManeger = OkHttpManeger.getInstance();
//        }
//        JSONObject object = okHttpManeger.obtainJson("reqType", StaticString.CHECK_VERSION);
//        okHttpManeger.post(object, new OkHttpManeger.ResponseHandler() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onSuccess(JSONObject object) {
//
//            }
//
//            @Override
//            public void onFail(Exception e) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });

        String newVersion = "";
        if (!newVersion.equals(version)){
            // 有新版本，弹出是否升级对话框
            showUpdateDialog();
        }else {
            enterHome();
        }
    }



    private void showUpdateDialog() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);

        build.setTitle("新版本提示信息");
        build.setMessage("下载新版本，免费送去东莞的机票");
        build.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                enterHome();
            }
        });
        build.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterHome();
            }
        });

        build.setNegativeButton("取消升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterHome();
            }
        });

        build.show();
    }

    //跳转到安全卫士的主界面
    private void enterHome() {
        startActivity(HomeActivity.class);
//        finish();
    }
}
