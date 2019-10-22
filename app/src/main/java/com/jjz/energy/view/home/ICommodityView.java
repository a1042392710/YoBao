package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.CommentBean;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;

/**
 * @Features: 商品详情
 * @author: create by chenhao on 2019/10/9
 */
public interface ICommodityView extends IBaseView {

    //获取商品详情
    void isGetGoodsDetailsSuc(GoodsDetailsBean data);


    //获取评论列表
    void isGetCommentSuc(CommentBean data);

    //发布评论
    void isPutCommentSuc(String data);

    //收藏或取消收藏成功
    void isPutCollectSuc(String data);

    void isFail(String msg, boolean isNetAndServiceError);
}
