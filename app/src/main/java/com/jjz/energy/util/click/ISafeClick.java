package com.jjz.energy.util.click;

import android.view.View;

/**
 * @author FX
 * @date 2018/6/23  15:08
 * @fuction
 */
public interface ISafeClick extends View.OnClickListener {
    public int MIN_CLICK_DELAY_TIME = 800;

    public abstract void safeClick(View view);
}