package com.jjz.energy.view.login;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.LoginBean;
import com.jjz.energy.entry.UserInfo;

/**
 * @ author FX
 * @ date  2018/12/7  16:00
 * @ fuction
 */
public interface ILoginView extends IBaseView {

    //登录成功
   default void getLoginSuc(UserInfo userInfo){};

    //登录失败
    default  void getLoginFail(String msg){};

    //提交第三方登录信息
    default    void isApiLoginSuc(LoginBean modifyLoginPassWordBean){};
}
