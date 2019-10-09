package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;

/**
 * @Features: 发布宝贝
 * @author: create by chenhao on 2019/10/9
 */
public interface IPutCommodityView extends IBaseView {

    //发布宝贝成功
    void isPutCommditySuccess(String data);

    void isFail(String msg,boolean isNetAndServiceError);


}
