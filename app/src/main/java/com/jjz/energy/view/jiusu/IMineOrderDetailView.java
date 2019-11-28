package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.OrderDetailBean;

/**
 * create 我的订单详情
 * Date: 2018/9/17 下午4:22
 */
public interface IMineOrderDetailView extends IBaseView {
    //订单详情
    void isSuccess(OrderDetailBean data);
    //取消订单
    default void isCancleOrderSuccess(String data){
    };

    void isFail(String msg);


}