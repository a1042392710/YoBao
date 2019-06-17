package com.jjz.energy.util.click;

import android.view.View;

import java.util.Calendar;

/**
 * @author FX
 * @date 2018/6/23  15:08
 * @fuction 防止快速点击
 */
public abstract class SafeClickListener implements ISafeClick {

    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            safeClick(v);
        }
    }
}