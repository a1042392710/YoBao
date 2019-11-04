package com.jjz.energy.view.order;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.order.RefundDetailsBean;

/**
 * @author chenhao 2018/9/30
 * @function  申请售后信息
 */
public interface IRefundView extends IBaseView {

    //获取售后详情
   default void isGetRefundDetailsSuccess(RefundDetailsBean data){

    };

    void isFail(String msg);

}
