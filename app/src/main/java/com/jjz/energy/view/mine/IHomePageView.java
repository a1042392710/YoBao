package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.entry.mine.UserPageInfo;
import com.jjz.energy.entry.commodity.GoodsListBean;

/**
 * create 个人主页
 * Date: 2018/9/17 下午4:22
 */
public interface IHomePageView extends IBaseView {


    //获取个人信息
    default  void isGetInfoSuccess(UserPageInfo data){

    };
    //关注或取消关注
    default  void isFocusUserSuccess(String data){

    };
    //获取用户所有的商品
    default  void isGetUserAllGoods(GoodsListBean data){

    };
    //获取用户所有的评价
    default  void isGetUserComments(HomePageCommentBean data){

    };

    void isFail(String msg, boolean isNetAndServiceError);


}