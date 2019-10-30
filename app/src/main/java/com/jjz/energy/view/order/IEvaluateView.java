package com.jjz.energy.view.order;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.order.EvaluateDetailsBean;

/**
 * @Features: 评价
 * @author: create by chenhao on 2019/10/9
 */
public interface IEvaluateView extends IBaseView {

    //提交评价信息
    default void isPutEvaluateInfoSuc(String data) {

    }

    //查看评价
    default void isGetEvaluateDetailsInfoSuc(EvaluateDetailsBean data) {

    }


    void isFail(String msg, boolean isNetAndServiceError);


}
