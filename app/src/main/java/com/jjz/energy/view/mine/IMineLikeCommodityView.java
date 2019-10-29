package com.jjz.energy.view.mine;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.mine.LikeGoodsBean;

/**
 * @author chenhao 2018/9/30
 * @function  我的收藏
 */
public interface IMineLikeCommodityView extends IBaseView {
    //获取收藏列表
    void isSuccess(LikeGoodsBean data);

    void isFail(String msg, boolean isNetAndServiceError);


    //取消收藏成功
    void isPutCollectSuc(String data);
}
