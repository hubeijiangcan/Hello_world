package com.mitbbs.summary.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mitbbs.summary.OkHttpManeger;
import com.mitbbs.summary.R;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private OkHttpManeger maneger;
    private TextView textView;
    final String  TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv_test);
        if (maneger == null) maneger = OkHttpManeger.getInstance();
        JSONObject jsonObject = maneger.obtainJson("reqType","search_job","occupation", "",
                "min_degree", "", "created_date", "","name",
                "","page",1+"");

        maneger.post(jsonObject, new OkHttpManeger.ResponseHandler() {
            @Override
            public void onStart() {
                Log.e(TAG,"onStart");
            }

            @Override
            public void onSuccess(JSONObject object) {
                Log.e(TAG,"onSuccess.object = "+object.toString());
            }

            @Override
            public void onFail(Exception e) {
                Log.e(TAG,"onFail"+((e == null)?"null":e.getCause()));

            }

            @Override
            public void onFinish() {
                Log.e(TAG,"onFinish");
            }
        });



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestActivity.class));
            }
        });

    }
}
