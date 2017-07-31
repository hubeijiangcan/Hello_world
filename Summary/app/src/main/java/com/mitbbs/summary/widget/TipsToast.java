package com.mitbbs.summary.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mitbbs.summary.R;

/**
 * Created by jc on 2017/5/8.
 */
public class TipsToast extends Toast{

    Context context;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public TipsToast(Context context) {
        super(context);
        this.context = context;
    }


    public static TipsToast makeText(Context context,CharSequence text,int duration){
        TipsToast result = new TipsToast(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_tips,null);
        TextView tv = (TextView) v.findViewById(R.id.tips_msg);
        tv.setText(text);
        result.setView(v);

        // setGravity方法用于设置位置，此处为垂直居中
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);
        return result;
    }


    public static TipsToast makeText(Context
                                             context, int resId, int duration) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    @Override
    public void setView(View view) {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.view_tips, null);
        super.setView(v);
    }

    public void setIcon(int iconResId){
        if (getView() == null){
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }

        ImageView iv = (ImageView) getView().findViewById(R.id.tips_icon);

        if (iv == null){
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }

        iv.setImageResource(iconResId);
    }

    @Override
    public void setText(CharSequence s) {
        if (getView() == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        TextView tv = (TextView) getView().findViewById(R.id.tips_msg);
        if (tv == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        tv.setText(s);
    }
}
