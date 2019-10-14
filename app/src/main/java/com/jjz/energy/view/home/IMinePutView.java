package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.LikeGoodsBean;

/**
 * @Features: 发布商品
 * @author: create by chenhao on 2019/10/9
 */
public interface IMinePutView extends IBaseView {

    //获取我发布的商品
    void isGetMineCommditySuccess(LikeGoodsBean data);

    //下架商品
    void isDownGoodsSuccess(String data);

    void isFail(String msg, boolean isNetAndServiceError);


}
