package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.mine.MineIntegralBean;

/**
 * create 获取积分
 * Date: 2018/9/17 下午4:22
 */
public interface IMineIntegralView extends IBaseView {
    //获取积分
    default  void isGetIntegralSuccess(MineIntegralBean data){

    };

    void isFail(String msg);


}