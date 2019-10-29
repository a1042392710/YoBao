package com.jjz.energy.view.order;

import com.jjz.energy.base.IBaseView;

/**
 * @Features: 评价
 * @author: create by chenhao on 2019/10/9
 */
public interface IEvaluateView extends IBaseView {

    //提交评价信息
    default void isPutEvaluateInfoSuc(String data) {

    }


    void isFail(String msg, boolean isNetAndServiceError);


}
