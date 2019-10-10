package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.GoodsBean;
import com.jjz.energy.entry.HomeDetailBean;

import java.util.List;

/**
 * @Features: 首页
 * @author: create by chenhao on 2019/10/9
 */
public interface IHomeView extends IBaseView {


    //获取分类
    void isGetClassificationSuc(HomeDetailBean data);

    void isFail(String msg, boolean isNetAndServiceError);

    //获取商品信息
    void isGetGoodsSuc(List<GoodsBean> data);

    void isGetGoodsFail(String msg, boolean isNetAndServiceError);


}
