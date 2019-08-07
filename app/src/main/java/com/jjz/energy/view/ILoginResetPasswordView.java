package com.jjz.energy.view;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.LoginBean;

/**
 * @author Ch
 * @fuction 忘记密码 提交验证码
 */
public interface ILoginResetPasswordView extends IBaseView {


    //验证成功
    void isSuccess(LoginBean loginBean);

    //验证失败
    void isFail(String msg);

}
