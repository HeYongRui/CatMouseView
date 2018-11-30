package com.heyongrui.catloadingview.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lambert on 2018/11/29.
 */

public class CatClawView extends View {
    //饼状图外圆半径
    private float mRadius = dip2px(60) + 3;
    //圆周率
    private final float PI = 3.1415926f;
    private final int PART_ONE = 1;
    private final int PART_TWO = 2;
    private final int PART_THREE = 3;
    private final int PART_FOUR = 4;

    private boolean mIsDrawCatClaw;
    private boolean mIsCanClick = true;
    private float mTouchX, mTouchY;
    private Bitmap mCatClawBitmap;

    private OnClickAngleListener mListener;

    public CatClawView(Context context) {
        this(context, null);
    }

    public CatClawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CatClawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化画笔和效果动画
    private void init() {
        //读取猫爪原图的1/2大小bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        mCatClawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_claw, options);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int length = (int) (2 * mRadius);
        setMeasuredDimension(length, length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsDrawCatClaw) {
            //在点击处绘制猫爪
            int catClawWidth = mCatClawBitmap.getWidth();
            int catClawHeight = mCatClawBitmap.getHeight();
            int offX = catClawWidth / 2;
            int offY = catClawHeight / 2;
            Rect bitmapSrc = new Rect(0, 0, catClawWidth, catClawHeight);
            Rect bitmapDst = new Rect((int) (mTouchX - offX), (int) (mTouchY - offY), (int) (mTouchX + offX), (int) (mTouchY + offY));
            canvas.drawBitmap(mCatClawBitmap, bitmapSrc, bitmapDst, null);
            mIsDrawCatClaw = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchEventHandling(event);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void touchEventHandling(MotionEvent event) {
        if (!mIsCanClick) return;
        mTouchX = event.getX();
        mTouchY = event.getY();
        float touchAngle = 0;
        //点击的位置到圆心距离的平方
        double distance = Math.pow(mTouchX - mRadius, 2) + Math.pow(mTouchY - mRadius, 2);
        //判断点击的坐标是否在环内(利用三角函数)
        if (distance < Math.pow(mRadius, 2) && distance > Math.pow(0.72 * mRadius, 2)) {
            int which = touchOnWhichPart(event);
            switch (which) {
                case PART_ONE:
                    touchAngle = (float) (Math.atan2(mTouchX - mRadius, mRadius - mTouchY) * 180 / PI);
                    break;
                case PART_TWO:
                    touchAngle = (float) (Math.atan2(mTouchY - mRadius, mTouchX - mRadius) * 180 / PI + 90);
                    break;
                case PART_THREE:
                    touchAngle = (float) (Math.atan2(mRadius - mTouchX, mTouchY - mRadius) * 180 / PI + 180);
                    break;
                case PART_FOUR:
                    touchAngle = (float) (Math.atan2(mRadius - mTouchY, mRadius - mTouchX) * 180 / PI + 270);
                    break;
            }
            //点击的是圆环内，绘制猫爪，并回调当前角度值
            mIsDrawCatClaw = true;
            invalidate();
            if (mListener != null) mListener.onClickAngle(this, touchAngle);
        }
    }

    /**
     * 4 |  1
     * -----|-----
     * 3 |  2
     * 圆被分成四等份，判断点击在园的哪一部分
     */
    private int touchOnWhichPart(MotionEvent event) {
        if (event.getX() > mRadius) {
            if (event.getY() > mRadius) return PART_TWO;
            else return PART_ONE;
        } else {
            if (event.getY() > mRadius) return PART_THREE;
            else return PART_FOUR;
        }
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setOnClickAngleListener(OnClickAngleListener listener) {
        this.mListener = listener;
    }

    public void setCanClick(boolean isCanClick) {
        mIsCanClick = isCanClick;
    }

    public interface OnClickAngleListener {
        void onClickAngle(CatClawView catClawView, float angle);
    }
}