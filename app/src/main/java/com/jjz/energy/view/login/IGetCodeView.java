package com.jjz.energy.view.login;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.LoginBean;

/**
 * create 获取验证码
 * Date: 2018/9/17 下午4:22
 */
public interface IGetCodeView extends IBaseView {
    //登录成功
  default   void isLoginSuccess(LoginBean data){

  };
    //获取验证码成功
    default void isGetCodeSuccess(String data){

    };
    //密码登录成功
    default void isPwdLoginSuccess(LoginBean data){

    };

    void isFail(String msg);


}