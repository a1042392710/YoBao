package com.jjz.energy.view.order;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.entry.mine.RefundHistroyBean;
import com.jjz.energy.entry.order.RefundDetailsBean;

/**
 * @author chenhao 2018/9/30
 * @function  申请售后信息
 */
public interface IRefundView extends IBaseView {

    //获取售后详情
   default void isGetRefundDetailsSuccess(RefundDetailsBean data){

    };

    //买家撤销申请
   default void isBuyerCancelApplicationSuccess(String data){

    };

    //买家提交退货信息
   default void isBuyerPutExpressInfoSuccess(String data){

    };

    //卖家同意退款
   default void isSellerAgreeReturnMoneySuccess(String data){

    };

    //卖家同意退货，发送退货地址
   default void isSellerGetAddressSuccess(AddressBean data){

    };

    //获取协商历史
   default void isGetHistorySuccess(RefundHistroyBean data){

    };
    /**
     *  获取物流地址信息
     */
    default void isSellerPutExpressInfoSuccess(String data) {

    }

    //卖家拒绝退款申请
   default void isSellerRefuseReturnMoneySuccess(String data){

    };




    void isFail(String msg);

}
