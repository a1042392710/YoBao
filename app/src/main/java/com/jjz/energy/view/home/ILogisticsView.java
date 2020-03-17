package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.home.LogisticsBean;
import com.jjz.energy.entry.home.LogisticsListBean;

/**
 * @Features: 物流专区
 * @author: create by chenhao on 2019/10/9
 */
public interface ILogisticsView extends IBaseView {

    //发布物流信息
    default void isPutLogisticsInfoSuc(String data) {}

    //查看物流列表
    default void isGetLogisticsInfoSuc(LogisticsListBean data) {}

    //查看物流详情
    default void isGetLogisticsDetailSuc(LogisticsBean data) {}

    //撤销物流发布
    default void isCancleLogisticsSuc(String data) {}

    void isFail(String msg, boolean isNetAndServiceError);
}
