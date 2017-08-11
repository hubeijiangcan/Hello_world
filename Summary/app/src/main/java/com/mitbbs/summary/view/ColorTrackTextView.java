package com.mitbbs.summary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mitbbs.summary.R;

/**
 * Created by jc on 2017/8/11.
 */
public class ColorTrackTextView extends TextView {

    private int originColor;
    private int changeColor;
    private Paint originPaint;
    private Paint changePaint;
    private float currentPross;
    //实现不同朝向
    private Distant distant = Distant.LEFT_TO_RIGHT;

    public void setDistant(Distant distant) {
        this.distant = distant;
    }


    public enum Distant{
        LEFT_TO_RIGHT,RIGHT_TO_LEFT
    }

    public ColorTrackTextView(Context context) {
        this(context,null);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs,defStyleAttr);
    }


    public void setCurrentPross(float currentPross) {
        this.currentPross = currentPross;
        invalidate();
    }

    public void setChangeColor(int changeColor) {
        changePaint.setColor(changeColor);
    }

    
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        originColor = a.getColor(R.styleable.ColorTrackTextView_originColor,getTextColors().getDefaultColor());
        changeColor = a.getColor(R.styleable.ColorTrackTextView_changeColor,getTextColors().getDefaultColor());
        a.recycle();

        originPaint =getPaintByColor(originColor);
        changePaint = getPaintByColor(changeColor);

    }

    private Paint getPaintByColor(int color){
        Paint paint = new Paint();
        paint.setTextSize(getTextSize());
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        paint.setColor(color);

        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int middle = (int) (currentPross*getWidth());

//        if (distant == Distant.LEFT_TO_RIGHT){
//            drawText(canvas,originPaint,middle,getWidth());
//            drawText(canvas,changePaint,0,middle);
//        }else {
//            drawText(canvas,originPaint,getWidth()-middle,getWidth());
//            drawText(canvas,changePaint,0,getWidth()-middle);
//        }

        // 从左变到右
        if(distant == Distant.LEFT_TO_RIGHT) {  // 左边是红色右边是黑色
            // 绘制变色
            drawText(canvas, changePaint , 0, middle);
            drawText(canvas, originPaint, middle, getWidth());
        }else{
            // 右边是红色左边是黑色
            drawText(canvas, changePaint, getWidth()-middle, getWidth());
            // 绘制变色
            drawText(canvas, originPaint, 0, getWidth()-middle);
        }

    }


    private void drawText(Canvas canvas,Paint paint,int start,int end){
        canvas.save();
        Rect bound = new Rect();
        paint.getTextBounds(getText().toString(),0,getText().length(),bound);
        int dx = (getWidth() - bound.width())/2;
        Paint.FontMetricsInt  fontMetrucs =  originPaint.getFontMetricsInt();
        int dy = (fontMetrucs.bottom - fontMetrucs.top)/2 - fontMetrucs.bottom;
        int baseLine = getHeight()/2 + dy;

        //默认颜色
        Rect rect = new Rect(start,0,end,getHeight());
        canvas.clipRect(rect);
        canvas.drawText(getText().toString(),dx,baseLine,paint);

        canvas.restore();
    }
}
