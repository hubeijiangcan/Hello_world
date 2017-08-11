package com.mitbbs.summary.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jc on 2017/8/11.
 */
public class CircleProgressBar extends View{

    private int mOuterColor;
    private int mInnerColor;
    private Paint mOuterPaint,mInnerPaint;
    private int textSize = 15;
    private int mBordWidth = 20;
    private int mTextColor = Color.BLACK;
    
    public CircleProgressBar(Context context) {
        this(context,null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mOuterColor = Color.RED;
        mInnerColor = Color.BLUE;


    }


}
