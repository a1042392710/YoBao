package com.jjz.energy.util;

/**
 * @author chenhao 2018/11/29
 * @function 弹窗辅助类
 */

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;

public class PopWindowUtil {

    /**
     * 中心弹出的自定义  PopWindow
     */
    private  PopupWindow popupCustomWindow;
    /**
     * 中心弹出的默认   PopWindow
     */
    private PopupWindow popupWindow ;
    /**
     * 底部弹出的   PopWindow
     */
    private PopupWindow bottomWindow;


    private PopWindowUtil() {

    }

    public static PopWindowUtil getInstance() {
        return new PopWindowUtil();
    }


    /**
     * 弹出自定义View
     */
    public PopupWindow showPopupWindow(Context context, View view) {

        int with = (int) ((double) getDisplayWith(context) * 0.77);
        popupCustomWindow = showPopupWindowByWidth(context, view, with);
        return popupCustomWindow;
    }

    public PopupWindow showPopupWindowByWidth(Context context, View view, int width) {

        popupCustomWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupCustomWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupCustomWindow.setOutsideTouchable(false);
        popupCustomWindow.setTouchable(true);
        popupCustomWindow.setFocusable(true);
        popupCustomWindow.setAnimationStyle(R.style.popwindow_center_anim_style);

        popupCustomWindow.setOnDismissListener(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //背景变亮
            ((BaseActivity) context).setDarkWindow(false);
        });
        //背景变暗
        ((BaseActivity) context).setDarkWindow(true);
        popupCustomWindow.showAtLocation(view, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);

        return  popupCustomWindow;
    }



    /**
     * 中心弹出的默认PopView
     */
    public PopupWindow showPopupWindow(Context context, String message, OnCountersignListener onCountersignListener) {

        View popView = LayoutInflater.from(context).inflate(R.layout.item_pop_defult_view, null);
        //弹窗占宽度的百分之75
        int with = (int) ((double)getDisplayWith(context)*0.75);
        popupWindow = new PopupWindow(popView, with, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwindow_center_anim_style);

        TextView tvMessage = popView.findViewById(R.id.pop_tv_message);
        tvMessage.setText(message);
        //取消
        popView.findViewById(R.id.pop_tv_cancle).setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        //pop确认
        popView.findViewById(R.id.pop_tv_ok).setOnClickListener(v -> {
            onCountersignListener.countersign();
            popupWindow.dismiss();
        });
        popupWindow.setOnDismissListener(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //背景变亮
            ((BaseActivity) context).setDarkWindow(false);
        });
        //背景变暗
        ((BaseActivity) context).setDarkWindow(true);
        popupWindow.showAtLocation(popView, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        return popupWindow;
    }
    /**
     * 中心弹出的默认PopView
     */
    public PopupWindow showPopupWindow(Context context, String message,String bottom1,String bottom2, OnCountersignAndCancleListener onCountersignListener) {

        View popView = LayoutInflater.from(context).inflate(R.layout.item_pop_defult_view, null);
        //弹窗占宽度的百分之75
        int with = (int) ((double)getDisplayWith(context)*0.75);
        popupWindow = new PopupWindow(popView, with, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwindow_center_anim_style);

        TextView tvMessage = popView.findViewById(R.id.pop_tv_message);
        tvMessage.setText(message);
        ((TextView)popView.findViewById(R.id.pop_tv_cancle)).setText(bottom1);
        ((TextView)popView.findViewById(R.id.pop_tv_ok)).setText(bottom2);
        //取消
        popView.findViewById(R.id.pop_tv_cancle).setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        //pop确认
        popView.findViewById(R.id.pop_tv_ok).setOnClickListener(v -> {
            onCountersignListener.countersign();
            popupWindow.dismiss();
        });
        popupWindow.setOnDismissListener(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //背景变亮
            ((BaseActivity) context).setDarkWindow(false);
        });
        //背景变暗
        ((BaseActivity) context).setDarkWindow(true);
        popupWindow.showAtLocation(popView, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        return popupWindow;
    }
    /**
     * 底部弹出的View
     */
    public PopupWindow showBottomWindow(Context context, View view) {
        bottomWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup. LayoutParams.WRAP_CONTENT, true);
        bottomWindow.setAnimationStyle(R.style.animation_bottom_dialog);
        bottomWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        bottomWindow.setOutsideTouchable(false);
        bottomWindow.setTouchable(true);
        bottomWindow.setFocusable(true);

        //popWindow 消失
        bottomWindow.setOnDismissListener(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //背景变亮
            ((BaseActivity) context).setDarkWindow(false);
        });
        //背景变暗
        ((BaseActivity) context).setDarkWindow(true);
        bottomWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        return bottomWindow;
    }


    public interface OnCountersignListener {
        //确认
        void countersign();
    }


    public interface OnCountersignAndCancleListener {
        //确认
        void countersign();
    }



    /**
     * 获取屏幕宽度
     */

    private int getDisplayWith (Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
       return dm.widthPixels;
    }
}
