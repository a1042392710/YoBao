package com.jjz.energy.view;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.mine.LikeGoodsBean;

/**
 * @author chenhao 2018/9/30
 * @function 首页
 */
public interface IMainView extends IBaseView {
    //提交推送RId
    default  void isSuccess(String data){}

    //获取搜索结果
    default void isGetSearchGoodsResultSuccess(LikeGoodsBean data){}



    void isFail(String msg);

}
