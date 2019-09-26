package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.entry.OrderDetailBean;

/**
 * create 个人信息
 * Date: 2018/9/17 下午4:22
 */
public interface IPersonalInformationView extends IBaseView {
    //提交个人信息
    default  void isSuccess(LoginBean data){

    };
    //获取个人信息
    default  void isGetInfoSuccess(LoginBean data){

    };
    //结单
    default void isFinishSuccess(String data){

    };
    //卖家订单详情
    default void isOrderInfoSuccess(OrderDetailBean data){

    };
    //提交推荐码
    default void isPutTjCodeSuccess(String data){

    };

    void isFail(String msg);


}