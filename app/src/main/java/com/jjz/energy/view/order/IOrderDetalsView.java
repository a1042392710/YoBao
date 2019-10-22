package com.jjz.energy.view.order;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.order.ShopOrderDetailsBean;

/**
 * @Features: 订单详情
 * @author: create by chenhao on 2019/10/9
 */
public interface IOrderDetalsView extends IBaseView {

    //获取商品信息
    void isGetOrderDetailsSuc(ShopOrderDetailsBean data);


    void isFail(String msg, boolean isNetAndServiceError);


}
