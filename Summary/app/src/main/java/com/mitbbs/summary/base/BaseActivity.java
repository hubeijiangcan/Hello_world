package com.mitbbs.summary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mitbbs.summary.MApplication;
import com.mitbbs.summary.R;
import com.mitbbs.summary.util.Utils;
import com.mitbbs.summary.view.SwipeBackLayout;

import java.io.Serializable;
import java.util.HashMap;


/**
 * Created by jc on 2017/4/1.
 */
public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener
    ,SwipeBackLayout.SwipeBackListener{
    public String TAG = "BaseActivity";
    public Context context;
    protected boolean pause;
    protected boolean lock;
    private boolean canSwipeBack = true;  //是否可以右滑返回，默认可以
    protected HashMap<String,Activity> activityMap;


    protected Toolbar toolbar;
    //右滑返回
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        context = this;
        addActivity(getClass().getSimpleName(),this);



        setContentView(attachLayout());
        bindViews();
    }

    /**
     *  关联布局
     * @return layout
     */
    protected abstract int attachLayout();

    /**
     * 初始化控件
     */
    protected abstract void bindViews();


    /**
     * 初始化标题栏
     * @param title
     */
    protected void initToolbar(String title){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        if (toolbar == null || toolbar_title == null)return;
        toolbar.setTitle("");
        toolbar_title.setText(title);
        toolbar_title.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        ActionBar actioBar = getSupportActionBar();
        if (actioBar == null)return;
        actioBar.setDisplayHomeAsUpEnabled(true);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_white_24px);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    protected MenuItem onCreatMenu(Menu menu, String title, @DrawableRes  int iconRes){
        MenuItem menuItem = menu.add(1,1,0,title).setIcon(iconRes);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return menuItem;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
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


    @Override
    protected void onStart() {
        super.onStart();
        //测试使用
        if (Utils.isDebugTest(this)){
            a();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause = true;
    }

    public boolean lock(){
        Log.e(TAG,"lock = " + lock + " pause = " + pause);
        if (lock || pause)return true;
        else {
            lock = true;
            return false;
        }
    }

    public boolean isLock(){
        return lock || pause;
    }

    public void setLock(boolean lock){
        this.lock = lock;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 右滑返回
     */
    @Override
    public void setContentView(int layoutResID) {
        View view;
        if (!canSwipeBack()){
            super.setContentView(layoutResID);
            return;
        }

        super.setContentView(getContainer());

        MApplication.getMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            view = getViewWithStatusBar(layoutResID);
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
            view.setElevation(24 * MApplication.DENSITY);
        }else {
            view = LayoutInflater.from(this).inflate(layoutResID,null);
        }

        swipeBackLayout.addView(view);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
    }


    private  View getViewWithStatusBar(int layoutResID){
        View contentView = LayoutInflater.from(this)
                .inflate(layoutResID,null);
        View statuBar = new View(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.
                LayoutParams.MATCH_PARENT,getStatusBarHeight());

        statuBar.setLayoutParams(params);

        TypedArray a = getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
        int colorPrimary = a.getColor(0,getResources().getColor(R.color.colorPrimary));

        statuBar.setBackgroundColor(colorPrimary);
        a.recycle();

        LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        view.addView(statuBar);
        view.addView(contentView);

        contentView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        return view;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0){
            result = getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setOnSwipeBackListener(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            ivShadow = new ImageView(this);
            ivShadow.setBackgroundColor(0x90000000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );

            container.addView(ivShadow,params);
        }

        container.addView(swipeBackLayout);
        return container;
    }

    private void setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        swipeBackLayout.setDragEdge(dragEdge);
    }
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            ivShadow.setAlpha(1 - fractionScreen);
        }
    }

    /**
     * 返回该界面是否可以右滑返回
     */
    private boolean canSwipeBack(){
        return canSwipeBack;
    }

    protected void startActivity(Class clazz){
        Intent intent = new Intent(context,clazz);
        startActivity(intent);
    }

    protected void startActivity(Class clazz, Object...objects){
        if (this.lock())return;

        if (objects.length%2 != 0){
            Log.e("BaseActivity","参数个数出错");
            return;
        }

        Intent intent = new Intent(context,clazz);
        for (int i =0;i<objects.length;i+=2){
            if (objects[i] instanceof String && objects[i+1] instanceof Serializable){
                intent.putExtra((String)objects[i],(Serializable)objects[i+1]);
            }else {
                Log.e("BaseActivity","参数类型出错");
                return;
            }
        }
        startActivity(intent);
        setLock(false);
    }

    protected void startActivityForResult(Class<?> clazz , int requestCode){
        Intent intent = new Intent(context,clazz);
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 先于setContentView调用，不然默认是可以右滑的
     * @param canSwipback
     */
    protected void setCanSwipeBack(boolean canSwipback){
        this.canSwipeBack = canSwipback;
    }


    private void a() {
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        if(toolbar == null || toolbar_title == null)return;
        toolbar_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                b();
                return true;
            }
        });
    }

    protected void b() {
    }

    protected void log(String s){
        Log.e(TAG,": " +s);
    }
}
