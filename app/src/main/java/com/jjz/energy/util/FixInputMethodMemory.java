package com.jjz.energy.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jjz.energy.base.BaseApplication;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * create by: xy
 * Date: 2018/9/12 上午9:10
 */

public class FixInputMethodMemory {
    /**
     * Fix for https://code.google.com/p/android/issues/detail?id=171190 .
     * <p>
     * When a view that has focus gets detached, we wait for the main thread to be idle and then
     * check if the InputMethodManager is leaking a view. If yes, we tell it that the decor view got
     * focus, which is what happens if you press home and come back from recent apps. This replaces
     * the reference to the detached view with a reference to the decor view.
     * <p>
     * Should be called from {@link Activity#onCreate(android.os.Bundle)} )}.
     */
    public static void fixFocusedViewLeak(Context context, EditText editText) {
        try {
            if (context == null) {
                return;
            }
            if (editText != null) {
                if (android.os.Build.VERSION.SDK_INT >= 19) {
                    ((InputMethodManager) Objects.requireNonNull(context.getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(
                            editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            InputMethodManager inputMethodManager = null;
            try {
                inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            } catch (Throwable th) {
                th.printStackTrace();
            }
            if (inputMethodManager == null) {
                return;
            }
            Field[] declaredFields = inputMethodManager.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                try {
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true);
                    }
                    Object obj = declaredField.get(inputMethodManager);
                    if (obj == null || !(obj instanceof View)) {
                        continue;
                    }
                    View view = (View) obj;
                    if (view.getContext() == context) {
                        declaredField.set(inputMethodManager, null);
                    } else {
                        continue;
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public static void showInput(final EditText et) {
        if (et == null) {
            return;
        }
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) BaseApplication.getAppContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideInput(final EditText et) {
        if (et == null) {
            return;
        }
        ((InputMethodManager) BaseApplication.getAppContext().getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(et
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
