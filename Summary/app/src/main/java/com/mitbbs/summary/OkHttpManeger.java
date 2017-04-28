package com.mitbbs.summary;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jc on 2017/4/26.
 *网络请求的api
 */
public class OkHttpManeger {
    private OkHttpClient client;
    private volatile static OkHttpManeger maneger;
    private final String TAG = "OkHttpManeger";
    private Handler handler;
    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    private OkHttpManeger(){
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    //采用单例模式获取对象
    public static OkHttpManeger getInstance(){
        OkHttpManeger instance = null;
        if (maneger == null){
            synchronized (OkHttpManeger.class){
                if (instance == null){
                    instance = new OkHttpManeger();
                    maneger = instance;
                }
            }
        }
        return maneger;
    }

    /**
     * 同步请求，在android开发中不常用
     * @param url
     * @return
     */
    @Deprecated
    public String syncGetByUrl(String url){
            //构建一个request请求
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();//同步请求数据
            if (response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 表单提交
     * @param params
     * @param callBack
     */
    public void post(Map<String, String> params, final ResponseHandler callBack){
        start(callBack);
        FormBody.Builder form_builder = new FormBody.Builder();//表单对象，包含以input开始的对象，以html表单为主
        if (params != null && !params.isEmpty()){
            for (Map.Entry<String,String> entry:params.entrySet()) {
                form_builder.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody request_body = form_builder.build();
        final Request request = new Request.Builder().url(StaticString.BASE_URL).post(request_body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                fail(callBack,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        success(callBack,object);
                    } catch (JSONException e) {
                        fail(callBack,e);
                    }
                }else {
                    fail(callBack,new RuntimeException("okHttpManeger post respone is null or fail"));
                }
            }
        });
    }

    public JSONObject obtainJson(Object...objects){
        JSONObject jsonObject = new JSONObject();
        if (objects.length %2 != 0){
            Log.e(TAG,"getJson参数个数出错");
            return null;
        }

        try {
            for (int i = 0; i<objects.length; i += 2){
                jsonObject.put((String)objects[i],objects[i+1]);
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return jsonObject;
    }
    /**
     * Json提交
     * @param params
     * @param callBack
     */
    public void post(JSONObject jsonObject,final ResponseHandler callBack){
        start(callBack);
        FormBody.Builder form_builder = new FormBody.Builder();//表单对象，包含以input开始的对象，以html表单为主
        form_builder.add("msg", jsonObject == null?"":jsonObject.toString());
        RequestBody request_body = form_builder.build();
        final Request request = new Request.Builder().url(StaticString.BASE_URL).post(request_body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                fail(callBack,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        success(callBack,object);
                    } catch (JSONException e) {
                        fail(callBack,e);
                    }
                }else {
                    success(callBack,new JSONObject());
                }
            }
        });
    }
    private void start(final ResponseHandler callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onStart();
            }
        });
    }

    private void success(final ResponseHandler callBack,final JSONObject object){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(object);
                callBack.onFinish();
            }
        });
    }

    private void fail(final ResponseHandler callBack, final Exception e){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFail(e);
                callBack.onFinish();
            }
        });
    }

    /**
     * 网络请求回调接口
     */
    public interface ResponseHandler{
        void onStart();
        void onSuccess(JSONObject object);
        void onFail(Exception e);
        void onFinish();
    }


}
