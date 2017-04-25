package com.mitbbs.summary.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Created by jc on 2017/4/21.
 */
public class TestUtils {
    /**
     * 是否正式版
     * @param context
     * @return
     */
    public static boolean isDebugTest(Context context){
        ApplicationInfo info = context.getApplicationInfo();
        if ((info.flags &ApplicationInfo.FLAG_DEBUGGABLE) == 0){
            Log.e("TestUtils" ," 非debug版本");
            return false;
        }
        return true;
    }
}
