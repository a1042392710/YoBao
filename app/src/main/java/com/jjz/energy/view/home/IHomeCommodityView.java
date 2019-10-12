package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.commodity.GoodsListBean;

/**
 * @Features: 首页 商品
 * @author: create by chenhao on 2019/10/9
 */
public interface IHomeCommodityView extends IBaseView {


    //获取商品信息
    void isGetGoodsSuc(GoodsListBean data);

    void isGetGoodsFail(String msg, boolean isNetAndServiceError);


}
