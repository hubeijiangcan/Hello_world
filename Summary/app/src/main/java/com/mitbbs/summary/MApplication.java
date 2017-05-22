package com.mitbbs.summary;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

/**
 * Created by jc on 2017/4/21.
 */
public class MApplication extends Application {

    private static MApplication mInstance;
    SharedPreferences mPreferences;
    public static float DENSITY = 0;
    public static int   WIDTH   = 0;
    public static int   HEIGHT  = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static MApplication getInstance(){
            return mInstance;
    }

    public static void getMetrics(){
        if(DENSITY != 0 && WIDTH != 0 && HEIGHT != 0)return;

        DisplayMetrics displayMetrics = new DisplayMetrics();
            displayMetrics = getInstance().getResources().getDisplayMetrics();
            DENSITY = displayMetrics.density;
            WIDTH   = displayMetrics.widthPixels;
            HEIGHT  = displayMetrics.heightPixels;
    }


    /**
     * /**
     * 返回当前程序版本名
     */
    public String getAppVersionName(Context context){
        String versionName = "";

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(),0);
            versionName = packageInfo.versionName;

            if (versionName == null || versionName.length()<=0){
                return "";
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }
}
