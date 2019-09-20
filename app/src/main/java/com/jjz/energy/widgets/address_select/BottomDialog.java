package com.jjz.energy.widgets.address_select;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.jjz.energy.R;


/**
 * Created by smartTop on 2016/10/19.
 */

public class BottomDialog extends Dialog {
    private AddressSelector selector;

    public BottomDialog(Context context) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        selector = new AddressSelector(context);
        setContentView(selector.getView());

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = dp2px(context, 356);
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);
    }

    private int dp2px(Context context, float dp) {
        return (int) Math.ceil((double) (context.getResources().getDisplayMetrics().density * dp));
    }

    public BottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public BottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public static BottomDialog show(Context context) {
        return show(context, null);
    }

    public static BottomDialog show(Context context, OnAddressSelectedListener listener) {
        BottomDialog dialog = new BottomDialog(context, R.style.bottom_dialog);
        dialog.selector.setOnAddressSelectedListener(listener);
        dialog.show();
        return dialog;
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        this.selector.setOnAddressSelectedListener(listener);
    }

    public void setDialogDismisListener(AddressSelector.OnDialogCloseListener listener) {
        this.selector.setOnDialogCloseListener(listener);
    }

    /**
     * 设置选中位置的监听
     *
     * @param listener
     */
    public void setSelectorAreaPositionListener(AddressSelector.onSelectorAreaPositionListener listener) {
        this.selector.setOnSelectorAreaPositionListener(listener);
    }

    /**
     * 设置字体选中的颜色
     */
    public void setTextSelectedColor(int selectedColor) {
        this.selector.setTextSelectedColor(selectedColor);
    }

    /**
     * 设置字体没有选中的颜色
     */
    public void setTextUnSelectedColor(int unSelectedColor) {
        this.selector.setTextUnSelectedColor(unSelectedColor);
    }

    /**
     * 设置字体的大小
     */
    public void setTextSize(float dp) {
        this.selector.setTextSize(dp);
    }

    /**
     * 设置字体的背景
     */
    public void setBackgroundColor(int colorId) {
        this.selector.setBackgroundColor(colorId);
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(int colorId) {
        this.selector.setIndicatorBackgroundColor(colorId);
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(String color) {
        this.selector.setIndicatorBackgroundColor(color);
    }

    /**
     * 设置已选中的地区
     *
     * @param provinceCode   省份code
     * @param cityCode       城市code
     * @param countyCode     乡镇code
     * @param streetCode     街道code
     */
    public void setDisplaySelectorArea(String provinceCode, String cityCode, String countyCode, String streetCode) {

        this.selector.getSelectedArea(provinceCode, cityCode, countyCode, streetCode);
    }

}
