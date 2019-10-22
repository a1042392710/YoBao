package com.jjz.energy.view.order;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.order.ExpressCompanyBean;

import java.util.List;

/**
 * @Features: 发布商品
 * @author: create by chenhao on 2019/10/9
 */
public interface IExpressCompanyView extends IBaseView {

    //发布商品成功
     void isGetExpressCompanySuc(List<ExpressCompanyBean> data) ;

    void isFail(String msg, boolean isNetAndServiceError);


}
