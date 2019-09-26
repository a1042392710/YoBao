package com.jjz.energy.view.login;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.entry.ModifyLoginPassWordBean;

/**
 * @ author FX
 * @ date  2018/12/7  16:00
 * @ fuction
 */
public interface ILoginView extends IBaseView {

    //登录成功
   default void getLoginSuc(LoginBean loginBean){};

    //登录失败
    default  void getLoginFail(String msg){};

    //获取验证码成功
    default  void getAuthCodeSuc(String loginBean){};

    //获取验证码失败
    default  void getAuthCodeFail(String msg){};

    //修改登录密码成功
    default   void getModifyLoginPassWordSuc(ModifyLoginPassWordBean modifyLoginPassWordBean){};

    //修改登录密码失败
    default   void getModifyLoginPassWordFail(String msg){};

    //提交第三方登录信息
    default    void isApiLoginSuc(LoginBean modifyLoginPassWordBean){};
}
