package com.jjz.energy.view.order;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.order.ApplicationRefundBean;

/**
 * @author chenhao 2018/9/30
 * @function  申请售后信息
 */
public interface IApplicationRefundView extends IBaseView {

    //获取申请售后信息成功
    void isSuccess(ApplicationRefundBean data);

    //提交售后申请成功
    void isSubmitSuccess(ApplicationRefundBean data);

    void isFail(String msg);

}
