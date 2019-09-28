package com.jjz.energy.util.networkUtil;


import com.jjz.energy.entry.UserInfo;

public interface UserLoginBizInterface {

    /**
     * 登录
     */
    void login();

    /**
     * 登录成功
     */
    void loginSuccess(UserInfo data);


    /**
     * 登录成功
     */
    void updataSuccess(UserInfo data);

    /**
     * 登录失败
     *
     * @param error
     */
    void loginFailed(String error);

    /**
     * 加载用户信息
     *
     * @return
     */
    UserInfo readUserInfo();

    /**
     * 保存用户信息
     *
     * @param info
     */
    void saveUserInfo(UserInfo info);

}
