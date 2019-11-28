package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;

/**
 * create 我的订单列表
 * Date: 2018/9/17 下午4:22
 */
public interface IMineOrderView extends IBaseView {
    //登录成功
    void isSuccess(JiuSuOrderBean data);

    void isFail(String msg);


}