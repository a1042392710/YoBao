package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.commodity.GoodsClassificationBean;

import java.util.List;

/**
 * @Features: 发布商品
 * @author: create by chenhao on 2019/10/9
 */
public interface IPutCommodityView extends IBaseView {

    //发布商品成功
    default   void isPutCommditySuccess(String data){

    };

    //发布商品成功
  default   void isGetClassificationSuc(List<GoodsClassificationBean> data){

  };

    void isFail(String msg,boolean isNetAndServiceError);


}
