package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.home.EntrustListBean;
import com.jjz.energy.wxapi.OrderPayTypeBean;

/**
 * create 委托大厅
 * Date: 2018/9/17 下午4:22
 */
public interface IEntrustListView extends IBaseView {

    //获取委托列表
    default void isGetEntrustListSuc(EntrustListBean data) {}


    void isFail(String msg, boolean isNetAndServiceError);

    //提交委托成功
    default void isPutEntrustSuc(OrderPayTypeBean msg){}

    //接受委托成功
    default void isAccpetEntrustSuc(String msg){}

    //完成委托
    default void isFinishEntrustSuc(String msg){}

    //取消委托
    default void isCancleEntrustSuc(String msg){}


}