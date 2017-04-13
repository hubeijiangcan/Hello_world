package com.mitbbs.summary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;


/**
 * Created by jc on 2017/4/1.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected HashMap<String,Activity> activityMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 每个创建的activity放进集合
     * @param name
     * @param activity
     */
    protected void addActivity(String name,Activity activity){
        if (activityMap == null){
            activityMap = new HashMap<>();
        }
        activityMap.put(name,activity);
    }

    protected void fiishByName(String name){
        Activity temp = activityMap.get(name);

        if (temp != null && !temp.isFinishing()){
            temp.finish();
        }else {
            Log.e("BaseActivity" , name + "不存在");
        }
    }
}
