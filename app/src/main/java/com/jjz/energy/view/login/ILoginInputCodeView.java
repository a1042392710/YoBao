package com.jjz.energy.view.login;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.UserInfo;

/**
 * @ author Ch
 * @ fuction
 */
public interface ILoginInputCodeView extends IBaseView {


    //获取验证码成功
    void getAuthCodeSuc(String loginBean);

    //获取验证码失败
    void getAuthCodeFail(String msg);

    //登录成功
    void loginVCodeSuc(UserInfo loginBean);

    //登录失败
    void loginVCodeFail(String msg);

    //忘记密码 提交验证码成功
    void forgotPasswordSuc(UserInfo loginBean);

    //忘记密码 提交验证码失败
    void forgotPasswordFail(String msg);

}
