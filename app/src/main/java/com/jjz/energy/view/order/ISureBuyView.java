package com.jjz.energy.view.order;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.commodity.GoodsBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShop;
import com.jjz.energy.wxapi.OrderPayTypeBean;

/**
 * @Features: 立即购买
 * @author: create by chenhao on 2019/10/9
 */
public interface ISureBuyView extends IBaseView {

    //获取商品信息
    default void isGetGoodsInfoSuc(GoodsBean data) {}

    //获取商家信息
    default void isGetShopsInfoSuc(JiuSuShop data) {}

    //获取支付信息
    default void isGetBuyInfoSuccess(OrderPayTypeBean data) {}



    void isFail(String msg, boolean isNetAndServiceError);


}
