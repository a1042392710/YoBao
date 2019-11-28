package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.MapMarkerBean;
import com.jjz.energy.entry.jiusu.ShopMarkerBean;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import java.util.List;

/**
 * create 首页地图
 * Date: 2018/9/17 下午4:22
 */
public interface IJiuSuHomeView extends IBaseView {
    //成功
    void isSuccess(List<MapMarkerBean> data);
    //成功获取具体网点信息
    void isShopInfoSuccess(ShopMarkerBean data);
    //创建订单成功 获取油价
    void isCreateOrderSuccess(OrderPayTypeBean data);

    void isFail(String msg);


}