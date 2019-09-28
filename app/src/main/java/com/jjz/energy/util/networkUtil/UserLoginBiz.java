package com.jjz.energy.util.networkUtil;

import android.content.Context;

import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.util.system.SpUtil;

import java.lang.ref.WeakReference;


/**
 * @author Administrator
 */
public class UserLoginBiz implements UserLoginBizInterface {

    public static UserLoginBiz instance = null;

    private static WeakReference<Context> mWeakReferenceContext = null;

    private static Context mContext = null;

    private UserLoginBiz() {
    }

    public static UserLoginBiz getInstance(Context context) {
        if (instance == null) {
            synchronized (UserLoginBiz.class) {
                if (instance == null) {
                    instance = new UserLoginBiz();
                    mWeakReferenceContext = new WeakReference<>(context);
                    mContext = mWeakReferenceContext.get().getApplicationContext();
                }
            }
        }
        return instance;
    }

    @Override
    public void login() {

    }

    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean detectUserLoginStatus() {
        return SpUtil.init(mContext).readBoolean("com.jjz.energy.eventbean.UserModel.LOGIN_STATUS", false);
    }
//    UserModel.LOGIN_STATUS
    /**
     * 登出
     */
    public void logout() {
        clearUserInfo();
        saveLoginStatus(false);
    }

    private void clearUserInfo() {
        SpUtil.init(mContext).clearAll();
    }

    @Override
    public void loginSuccess(UserInfo data) {
        // 保存用户信息
        saveUserInfo(data);
        // 保存登录状态
        saveLoginStatus(true);
    }

    /**
     * 保存登录状态
     *
     * @param isLogin
     */
    private void saveLoginStatus(Boolean isLogin) {
        SpUtil.init(mContext).commit("com.jjz.energy.eventbean.UserModel.LOGIN_STATUS", isLogin);
    }


    @Override
    public UserInfo readUserInfo() {
        return SpSaveClass.getInstance(mContext).readClass(UserInfo.class)==null?new UserInfo():SpSaveClass.getInstance(mContext).readClass(UserInfo.class);
    }

    @Override
    public void saveUserInfo(UserInfo info) {
        SpSaveClass.getInstance(mContext).saveClass(info);
    }

    @Override
    public void updataSuccess(UserInfo data) {
        // 保存用户信息
        saveUserInfo(data);
        // 保存登录状态
        saveLoginStatus(true);
    }

    @Override
    public void loginFailed(String error) {

    }

}
