package com.jjz.energy.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 实现单击返回 imageView.setOnPhotoTapListener(new OnPhotoTapListener() {
 *
 * @author Administrator
 * @Override public void onPhotoTap(View arg0, float arg1, float arg2) {
 * context.finish(); } });
 */
public class FxScaleBUGViewPager extends ViewPager {

    public FxScaleBUGViewPager(Context context) {
        super(context);
    }

    public FxScaleBUGViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            // uncomment if you really want to see these errors
            // e.printStackTrace();
            return false;
        }
    }

}
