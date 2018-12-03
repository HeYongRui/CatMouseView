package com.heyongrui.catmouseview.library;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;


/**
 * Created by lambert on 2018/11/30.
 */

public class CatMouseDialog extends Dialog {

    private CatMouseView mCatMouseView;

    public CatMouseDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    public CatMouseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        getWindow().setGravity(Gravity.CENTER);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCatMouseView = new CatMouseView(context);
        setContentView(mCatMouseView);
    }

    public void setBgColor(int bgColor) {
        if (mCatMouseView == null) return;
        mCatMouseView.setBgColor(bgColor);
    }

    public void setBgFilletRadius(int bgFilletRadius) {
        if (mCatMouseView == null) return;
        mCatMouseView.setBgFilletRadius(bgFilletRadius);
    }

    public void setAnimDuration(long animDuration) {
        if (mCatMouseView == null) return;
        mCatMouseView.setAnimDuration(animDuration);
    }

    public void setIsShowGraduallyText(boolean isShowGraduallyText) {
        if (mCatMouseView == null) return;
        mCatMouseView.setIsShowGraduallyText(isShowGraduallyText);
    }

    public void setGraduallyText(CharSequence graduallyText) {
        if (mCatMouseView == null) return;
        mCatMouseView.setGraduallyText(graduallyText);
    }

    @Override
    public void show() {
        super.show();
        if (mCatMouseView == null) return;
        mCatMouseView.startAnim();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mCatMouseView == null) return;
        mCatMouseView.stopAnim();
        mCatMouseView.destroy();
    }
}
