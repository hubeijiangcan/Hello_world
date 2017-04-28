package com.mitbbs.summary.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by jc on 2017/4/21.
 */
public class Utils {
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


    /**
     * 把一个jsonObject转换成一个class
     *
     * ***字段类型只能是 string
     */
    public static Object getInstanseFromeJSONObject(Class<?> cls, JSONObject jsonObject){
        if (jsonObject == null)return null;

        Iterator<String> iterator = jsonObject.keys();
        Object obj = null;
        try {
            obj = cls.newInstance();
            while (iterator.hasNext()){
                String fieldName = iterator.next();
                Object o = jsonObject.optString(fieldName);//字段类型只能是 strin
                setter(obj,fieldName,o,cls.getDeclaredField(fieldName).getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static void setter(Object obj, String field, Object value, Class<?> type) {
        try{
            Method method = obj.getClass().getMethod("set" + updateFirst(field),type);
            method.invoke(obj,value);
        }catch (Exception e){
            Log.e("tag","getInstanceFromJsonObject error!2" + e.toString());
            e.printStackTrace();
        }
    }

    private static String  updateFirst(String fldName) {
        String first = fldName.substring(0, 1).toUpperCase();
        String rest = fldName.substring(1, fldName.length());
        String newStr = new StringBuffer(first).append(rest).toString();
        return newStr;
    }
}
