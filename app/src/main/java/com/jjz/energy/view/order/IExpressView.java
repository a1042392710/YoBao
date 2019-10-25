package com.jjz.energy.view.order;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.order.ExpressAddressInfoBean;
import com.jjz.energy.entry.order.ExpressCompanyBean;

import java.util.List;

/**
 * @Features: 物流的接口
 * @author: create by chenhao on 2019/10/9
 */
public interface IExpressView extends IBaseView {

    //获取物流公司成功
    default void isGetExpressCompanySuc(List<ExpressCompanyBean> data) {

    }

    //发货成功
    default void isPutExpressInfoSuc(String data) {

    }

    //面交成功
    default void isPutMeetingTransactionSuc(String data) {

    }

    //获取物流地址信息
    default void isGetExpressAddressInfoSuc(ExpressAddressInfoBean data) {

    }

    void isFail(String msg, boolean isNetAndServiceError);


}
