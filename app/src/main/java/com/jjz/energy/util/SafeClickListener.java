package com.jjz.energy.util;

import android.view.View;

public abstract class SafeClickListener implements View.OnClickListener {
    private long mLastClickTime;
    private long timeInterval = 1000L;

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick(v);
            mLastClickTime = nowTime;
        }
    }

    protected abstract void onSingleClick(View v);


}