package com.mitbbs.summary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by jc on 2017/4/24.
 */
public class BaseFragment extends Fragment{

    public String TAG = this.getClass().getSimpleName();
    private boolean pause;
    private boolean lock;

    private boolean isPrepared;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;



    protected void loge(String s){
//        Log.e(this.getClass().getSimpleName() ,"index:"+ currentIndex + " : " + s);
    }
    protected void baseLog(String s){
//        Log.e("cycle","Index :"+currentIndex + " : "+ s);
    }

    protected void log(String s){
        Log.e(TAG,s);
    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("onResume" + "getUserVisibleHint  = " + super.getUserVisibleHint());

        if (isFirstResume){
            isFirstResume = false;
            return;
        }

        if (getUserVisibleHint()){
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pause = true;
        log("onPause");
        if (getUserVisibleHint()){
            onUserVisible();
        }
    }

    protected boolean lock(){
        if (lock||pause)return true;
        else {
            setLock(true);
            return false;
        }
    }

    protected void setLock(boolean lock) {
        this.lock = lock;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreat");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log("onCretView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("onDEsroyView");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("onActivityCreated");
        initPrepare();
    }

    private void initPrepare() {
        if (isPrepared){
            onFirstUsersVisible();
        }else {
            isPrepared = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        log("setUserVisibleHint  = " + isVisibleToUser);
        if (isVisibleToUser){
            if (isFirstInvisible){
                isFirstVisible = false;
                initPrepare();
            }else {
                onUserVisible();
            }
        }else {
            if(isFirstInvisible){
                isFirstInvisible = false;
                onFirstUserInvisible();
            }else {
                onUserInvisible();
            }
        }
    }

    private void onUserInvisible() {
    }

    private void onFirstUserInvisible() {
    }

    private void onFirstUsersVisible() {
    }

    protected void onUserVisible(){

    }

    protected void startActivity(Class clz,Object...objects){
        if (this.lock())return;

        if (objects.length%2 != 0){
            Log.e("BaseFragment","参数个数出错");
            return;
        }

        Intent intent = new Intent(getContext(),clz);

        for(int i=0;i<objects.length;i+=2){
            if(objects[i] instanceof String && objects[i+1] instanceof Serializable){
                intent.putExtra((String)objects[i],(Serializable)objects[i+1]);
            }else {
                Log.e("SearchUserAdapter","参数类型出错");
                return;
            }
        }

        startActivity(intent);
        setLock(true);
    }

    @Override
    public void startActivity(Intent intent) {
        pause = true;
        super.startActivity(intent);
    }
}
