package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.UserPageInfo;

/**
 * create 个人主页
 * Date: 2018/9/17 下午4:22
 */
public interface IHomePageView extends IBaseView {


    //获取个人信息
    default  void isGetInfoSuccess(UserPageInfo data){

    };
    //关注或取消关注
    default  void isFocusUserSuccess(String data){

    };

    void isFail(String msg, boolean isNetAndServiceError);


}