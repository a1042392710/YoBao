package com.jjz.energy.view.login;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.UserInfo;

/**
 * @author Ch
 * @fuction 忘记密码 提交验证码
 */
public interface ILoginResetPasswordView extends IBaseView {


    //验证成功
    default void isSuccess(UserInfo loginBean){

    };

    //验证成功
    default  void  isSettingPwSuccess(String str){

    }

    //验证失败
    void isFail(String msg,boolean isNetAndServiceError);

}
