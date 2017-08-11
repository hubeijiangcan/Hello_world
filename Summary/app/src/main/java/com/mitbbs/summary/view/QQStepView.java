package com.mitbbs.summary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.renderscript.Type;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.mitbbs.summary.R;

/**
 * Created by jc on 2017/8/10.
 */
public class QQStepView extends View{

    private int mOuterColor;
    private int mInnerColor;
    private int mSetpTextSize = 15;
    private int mBoardSize;  //px
    private int mStepTextColor;

    private Paint mOuterPaint,mInnerPaint,mTextPaint;
    private int mWidth;

    private int maxValue,currentValue;
    private float sweepAngle;

    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        mOuterColor = Color.RED;
        mInnerColor = Color.BLUE;
        mBoardSize = 20;//px
        mStepTextColor = Color.BLACK;

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.QQStepView);
        mOuterColor = a.getColor(R.styleable.QQStepView_outerColor,mOuterColor);
        mInnerColor = a.getColor(R.styleable.QQStepView_innerColor,mInnerColor);
        mSetpTextSize = a.getDimensionPixelSize(R.styleable.QQStepView_setpTextSize,sp2px(mSetpTextSize));
        mBoardSize = (int) a.getDimension(R.styleable.QQStepView_boardWidth,mBoardSize);
        mStepTextColor = a.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);
        a.recycle();

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStrokeWidth(mBoardSize);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeWidth(mBoardSize);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mSetpTextSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = width>height?height:width;
        setMeasuredDimension(mWidth,mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外弧
        RectF rect = new RectF(mBoardSize/2,mBoardSize/2,mWidth-mBoardSize/2,mWidth-mBoardSize/2);
        canvas.drawArc(rect,135,270,false,mOuterPaint);

        if (maxValue == 0){
            return;
        }
        sweepAngle = 270*currentValue/maxValue;
        //画内弧
        canvas.drawArc(rect,135, sweepAngle,false,mInnerPaint);

        //画文字
        String content = currentValue+"";
        Rect rectT = new Rect();

        mTextPaint.getTextBounds(content,0,content.length(),rectT);
        int dx = (getWidth() - rectT.width())/2;
        //基线
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int baseLine = getHeight()/2 +(fontMetricsInt.bottom-fontMetricsInt.top)/2 - fontMetricsInt.bottom;
//        int baseLine = -fontMetricsInt.top;

        canvas.drawText(content,dx,baseLine,mTextPaint);
    }


    public void setMaxValue(int maxValue){
        this.maxValue = maxValue;
    }

    public void setCurrentValue(int currentValue){
        this.currentValue = currentValue;
        invalidate();
    }

    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,getResources().getDisplayMetrics());
    }
}
