package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.NoticeListInfo;
import com.jjz.energy.entry.mine.OrderNoticeBean;

/**
 * @Features: 首页
 * @author: create by chenhao on 2019/10/9
 */
public interface INoticeView extends IBaseView {


    //获取订单消息列表
    default void isGetOrderNoticeSuc(OrderNoticeBean data){

    }

    //获取最新的所有消息
    default void isGetNoticeListInfoSuc(NoticeListInfo data){

    }

    void isFail(String msg);
}
