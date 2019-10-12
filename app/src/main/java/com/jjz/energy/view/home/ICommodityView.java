package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;

/**
 * @Features: 商品详情
 * @author: create by chenhao on 2019/10/9
 */
public interface ICommodityView extends IBaseView {

    //获取商品详情
    void isGetGoodsDetailsSuc(GoodsDetailsBean data);

    void isFail(String msg, boolean isNetAndServiceError);
}
