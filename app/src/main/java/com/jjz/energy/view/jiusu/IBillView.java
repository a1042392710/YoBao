package com.jjz.energy.view.jiusu;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.BillEntry;

/**
 * @author chenhao 2018/9/30
 * @function  发票详情
 */
public interface IBillView extends IBaseView {
    //发票提交
   default void isBillSubmitSuccess(String data){

   };
    //查看发票图片
    default void getBillSuccess(BillEntry data){

    };
    void isFail(String msg);

}
