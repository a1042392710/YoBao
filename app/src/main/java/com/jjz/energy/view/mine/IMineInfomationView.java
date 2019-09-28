package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.UserInfo;

/**
 * create 个人信息
 * Date: 2018/9/17 下午4:22
 */
public interface IMineInfomationView extends IBaseView {
    //提交个人信息
    default  void isSuccess(UserInfo data){

    };

    //获取个人信息
    default  void isGetInfoSuccess(UserInfo data){

    };

    //提交推荐码
    default void isPutTjCodeSuccess(String data){

    };

    void isFail(String msg, boolean isNetAndServiceError);


}