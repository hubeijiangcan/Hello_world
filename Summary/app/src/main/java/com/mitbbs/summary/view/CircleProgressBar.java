package com.mitbbs.summary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mitbbs.summary.R;

/**
 * Created by jc on 2017/8/11.
 */
public class CircleProgressBar extends View{

    private int mOuterColor;
    private int mInnerColor;
    private Paint mOuterPaint,mInnerPaint;
    private Paint mTextPaint;
    private float textSize = 15;
    private int mBordWidth = 20;
    private int mTextColor = Color.BLACK;

    private int mCurrentProgress = 0;
    private int totalProgress = 100;
    
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

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mOuterColor = array.getColor(R.styleable.CircleProgressBar_nomarlColor,mOuterColor);
        mInnerColor = array.getColor(R.styleable.CircleProgressBar_progressColor,mInnerColor);
        textSize = array.getDimension(R.styleable.CircleProgressBar_circleTextSize,sp2px(textSize));
        mBordWidth = array.getDimensionPixelOffset(R.styleable.CircleProgressBar_circleBoardWidth,mBordWidth);
        mTextColor = array.getColor(R.styleable.CircleProgressBar_circleTextColor,mTextColor);
        array.recycle();

        mOuterPaint = getPaintByColor(mOuterColor);
        mInnerPaint = getPaintByColor(mInnerColor);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(textSize);
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStrokeWidth(mBordWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画圆圈
        canvas.drawCircle(getWidth()/2,getWidth()/2 ,getWidth()/2 - mBordWidth/2,mOuterPaint);

        //画进度弧线

        RectF rect = new RectF(mBordWidth/2,mBordWidth/2,getWidth()-mBordWidth/2,getHeight() - mBordWidth/2);
        canvas.drawArc(rect,0,360*mCurrentProgress/totalProgress,false,mInnerPaint);

        //画文字
        String mtext = 100*mCurrentProgress/totalProgress + "%";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(mtext,0,mtext.length(),textBounds);
        int dx = (getWidth() - textBounds.width())/2;
        Paint.FontMetricsInt fontMetricsInt =  mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(mtext,dx,baseLine,mTextPaint);

    }

    public void setmCurrentProgress(int mCurrentProgress) {
        this.mCurrentProgress = mCurrentProgress;
        invalidate();
    }
}
