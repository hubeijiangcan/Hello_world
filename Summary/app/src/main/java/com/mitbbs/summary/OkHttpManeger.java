package com.mitbbs.summary;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        return instance;
    }


    private void post(final ResponseHandler callBack){
        final Request request = new Request.Builder().url(StaticString.BASE_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        callBack.onSuccess(object);
                    } catch (JSONException e) {
                        callBack.onFail(e);
                    }
                }else {
                    callBack.onFail(new RuntimeException("response is null"));
                }
            }
        });
    }
    interface ResponseHandler{
        void onStart();
        void onSuccess(JSONObject object);
        void onFail(Exception e);
        void onFinish();
    }


}
