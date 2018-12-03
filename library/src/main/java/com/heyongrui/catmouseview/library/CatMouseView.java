package com.heyongrui.catmouseview.library;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * Created by lambert on 2018/10/15.
 */

public class CatMouseView extends ConstraintLayout {

    private int mBgColor;//背景色
    private int mBgFilletRadius;//背景圆角半径
    private long mAnimDuration;//猫鼠动画一周动画时间

    private View mouse, smileCat, eyeLeft, eyeRight;//猫鼠控件
    private EyelidView eyelidLeft, eyelidRight;//猫眼睑
    private GraduallyTextView mGraduallyTextView;//逐字渐显控件
    private AnimatorSet mAnimatorSet;//猫鼠动画
    private int mCatchTime;//抓住老鼠次数
    private float mRotateAngle;//猫鼠动画当前旋转角度
    //抓住老鼠后的延时操作
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    public CatMouseView(Context context) {
        this(context, null);
    }

    public CatMouseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CatMouseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        //初始化参数和属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CatMouseView);
        mBgColor = typedArray.getColor(R.styleable.CatMouseView_bg_color, 0xff9ec7f5);
        mBgFilletRadius = (int) typedArray.getDimension(R.styleable.CatMouseView_bg_fillet_radius, dp2px(10));
        mAnimDuration = (long) typedArray.getFloat(R.styleable.CatMouseView_anim_duration, 2000);
        boolean isShowGraduallyText = typedArray.getBoolean(R.styleable.CatMouseView_is_show_gradually_text, true);
        CharSequence graduallyText = typedArray.getText(R.styleable.CatMouseView_gradually_text);
        typedArray.recycle();
        //加载布局文件
        View inflate = inflate(context, R.layout.layout_cat_catch_mouse, null);
        addView(inflate);
        //设置背景底图和圆角半径
        GradientDrawable gradientDrawable = new DrawableUtil.DrawableBuilder(context).setGradientRoundRadius(mBgFilletRadius)
                .setColor(mBgColor).createGradientDrawable();
        setBackgroundDrawable(gradientDrawable);
        //初始化控件
        mouse = inflate.findViewById(R.id.mouse);
        CatClawView catClawView = inflate.findViewById(R.id.pie_chart_view);
        smileCat = inflate.findViewById(R.id.smile_cat);
        eyeLeft = inflate.findViewById(R.id.eye_left);
        eyeRight = inflate.findViewById(R.id.eye_right);
        eyelidLeft = inflate.findViewById(R.id.eyelid_left);
        eyelidRight = inflate.findViewById(R.id.eyelid_right);
        mGraduallyTextView = inflate.findViewById(R.id.graduallyTextView);
        //控件监听器和属性设置
        eyelidLeft.setColor(Color.parseColor("#d0ced1"));
        eyelidRight.setColor(Color.parseColor("#d0ced1"));
        catClawView.setOnClickAngleListener((catClawView1, angle) -> {
            //猫爪点击回调，老鼠旋转起始角度和手势触控旋转起始角度不同，相差180°，故先对手势点击角度进行处理再进行比较
            double touchAngle = angle > 180 ? (angle - 180) : (angle + 180);
            float rotateAngle = mRotateAngle > 360 ? mRotateAngle % 360 : mRotateAngle;
            double abs = Math.abs((rotateAngle - touchAngle));
            if (abs <= 10) {//如果老鼠头的旋转角度和点击处角度误差不超过10°，就认为抓住了老鼠
                stopRotateAnim();
                catClawView1.setCanClick(false);
                smileCat.setVisibility(View.VISIBLE);
                mGraduallyTextView.stopLoading();
                mGraduallyTextView.setText("C A U G H T ");
                mCatchTime++;
                //等待2秒后加大游戏难度
                mRunnable = () -> {
                    smileCat.setVisibility(View.GONE);
                    catClawView1.invalidate();
                    catClawView1.setCanClick(true);
                    startRotateAnim();
                    mGraduallyTextView.setText("C A T C H I N G ... ");
                    mGraduallyTextView.startLoading();
                };
                mHandler.postDelayed(mRunnable, 2000);
            }
        });
        mGraduallyTextView.setVisibility(isShowGraduallyText ? VISIBLE : GONE);
        if (!TextUtils.isEmpty(graduallyText)) {
            mGraduallyTextView.setText(graduallyText);
        }
    }

    private void startRotateAnim() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) return;
        float rotation = mouse.getRotation();
        rotation = rotation > 720 ? rotation % 720 : rotation;
        ObjectAnimator mouseRotationAnim = ObjectAnimator.ofFloat(mouse, "rotation", 360f + rotation, rotation);
        long duration = mAnimDuration - mCatchTime * 500;//根据抓住老鼠的次数动态设置旋转时间，提升游戏难度，每次加快500毫秒
        duration = duration < 400 ? 400 : duration;//控制最快时间为400毫秒
        mouseRotationAnim.setDuration(duration);//设置动画时间
        mouseRotationAnim.setInterpolator(new LinearInterpolator());//动画时间线性渐变
        mouseRotationAnim.setRepeatCount(ObjectAnimator.INFINITE);
        mouseRotationAnim.setRepeatMode(ObjectAnimator.RESTART);
        ObjectAnimator leftEyeRotationAnim = mouseRotationAnim.clone();
        leftEyeRotationAnim.setTarget(eyeLeft);
        ObjectAnimator rightEyeRotationAnim = mouseRotationAnim.clone();
        rightEyeRotationAnim.setTarget(eyeRight);
        mAnimatorSet = new AnimatorSet();
        mouseRotationAnim.addUpdateListener(valueAnimator -> {
            mRotateAngle = (float) valueAnimator.getAnimatedValue();
            updateEyelidHeight(mRotateAngle);
        });
        mAnimatorSet.playTogether(mouseRotationAnim, leftEyeRotationAnim, rightEyeRotationAnim);
        mAnimatorSet.start();
    }

    private void stopRotateAnim() {
        if (mAnimatorSet == null || !mAnimatorSet.isRunning()) return;
        mAnimatorSet.cancel();
    }

    private void updateEyelidHeight(float rotateAngle) {
        //根据猫眼的旋转角度，动态设置猫眼眼睑高度(解决不同机型可能出现的bug)
        rotateAngle = rotateAngle > 360 ? rotateAngle % 360 : rotateAngle;
        if (60 <= rotateAngle && rotateAngle < 180) {
            eyelidLeft.setEyelidHeightPercent((180 - rotateAngle) / 120f);
            eyelidRight.setEyelidHeightPercent((180 - rotateAngle) / 120f);
            return;
        }
        if (180 <= rotateAngle && rotateAngle <= 300) {
            eyelidLeft.setEyelidHeightPercent(1 - (300 - rotateAngle) / 120f);
            eyelidRight.setEyelidHeightPercent(1 - (300 - rotateAngle) / 120f);
            return;
        }
        eyelidLeft.setEyelidHeightPercent(1);
        eyelidRight.setEyelidHeightPercent(1);
    }

    private int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setBgColor(int bgColor) {
        mBgColor = bgColor;
        GradientDrawable gradientDrawable = new DrawableUtil.DrawableBuilder(getContext()).setGradientRoundRadius(mBgFilletRadius)
                .setColor(bgColor).createGradientDrawable();
        setBackgroundDrawable(gradientDrawable);
    }

    public void setBgFilletRadius(int bgFilletRadius) {
        mBgFilletRadius = bgFilletRadius;
        GradientDrawable gradientDrawable = new DrawableUtil.DrawableBuilder(getContext()).setGradientRoundRadius(bgFilletRadius)
                .setColor(mBgColor).createGradientDrawable();
        setBackgroundDrawable(gradientDrawable);
    }

    public void setAnimDuration(long animDuration) {
        this.mAnimDuration = animDuration;
    }

    public void setIsShowGraduallyText(boolean isShowGraduallyText) {
        mGraduallyTextView.setVisibility(isShowGraduallyText ? VISIBLE : GONE);
    }

    public void setGraduallyText(CharSequence graduallyText) {
        if (TextUtils.isEmpty(graduallyText)) return;
        mGraduallyTextView.setText(graduallyText);
    }

    public boolean isRunning() {
        if (mAnimatorSet == null) return false;
        return mAnimatorSet.isRunning();
    }

    public void startAnim() {
        startRotateAnim();
        if (mGraduallyTextView == null) return;
        mGraduallyTextView.startLoading();
    }

    public void stopAnim() {
        stopRotateAnim();
        if (mGraduallyTextView == null) return;
        mGraduallyTextView.stopLoading();
    }

    public void destroy() {
        //移除延时任务，避免内存泄漏
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
