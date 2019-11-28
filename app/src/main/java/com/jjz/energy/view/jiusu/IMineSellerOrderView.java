package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;

/**
 * create 卖家订单列表
 * Date: 2018/9/17 下午4:22
 */
public interface IMineSellerOrderView extends IBaseView {
    //获取订单列表
    void isSuccess(JiuSuOrderBean data);
    //接受订单成功
    void isConfirmOrderSuccess(String data);

    void isFail(String msg);


}