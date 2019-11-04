package com.jjz.energy.view.mine;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.mine.MineBuyerBean;

/**
 * @author chenhao 2018/9/30
 * @function 我买到的
 */
public interface IMineBuyerView extends IBaseView {

    //获取购买列表
    void isSuccess(MineBuyerBean data);

    //确认收货
   default void isConfirmReceiptSuc(String data){

   };

    //成功取消订单
   default void isCancelOrderSuc(String data){

   };

    void isFail(String msg, boolean isNetAndServiceError);


}
