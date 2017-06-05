package com.mitbbs.summary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mitbbs.summary.R;

/**
 * Created by jc on 2017/5/24.
 */
public class SettingItemView extends RelativeLayout{

    private TextView tv_isUpdate;
    private CheckBox cb_isUpdate;
    private TextView tv_title;
    private String update_on;
    private String update_off;
    private String title;

    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.SettingItemView);

        if (a != null){
            update_on = a.getString(R.styleable.SettingItemView_update_on);
            update_off = a.getString(R.styleable.SettingItemView_update_off);
            title = a.getString(R.styleable.SettingItemView_setting_title);
        }
        initView(context);
//        update_off = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","update_on")+"";
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.setting_item_view,this);
        tv_title = (TextView) view.findViewById(R.id.tv_update);
        tv_isUpdate = (TextView) view.findViewById(R.id.tv_isUpdate);
        cb_isUpdate = (CheckBox) view.findViewById(R.id.cb_isUpdate);

        tv_title.setText(title);
        tv_isUpdate.setText(update_off);
    }

    public boolean isChecked(){
        return cb_isUpdate.isChecked();
    }

    public void setChecked(boolean checked){
        if (checked){
            tv_isUpdate.setText(update_on);
        }else {
            tv_isUpdate.setText(update_off);
        }
        cb_isUpdate.setChecked(checked);
    }

    public void setText(String content){
        tv_isUpdate.setText(content);
    }

    public void setText(int idRes){
        setText(getResources().getString(idRes));
    }

    public void setTitle(String content){
        tv_title.setText(content);
    }

    public void setTitle(int idRes){
        setText(getResources().getString(idRes));
    }
}
