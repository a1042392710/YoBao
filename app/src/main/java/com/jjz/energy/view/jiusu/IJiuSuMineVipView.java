package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.YoCardReceiveListBean;

import java.util.List;

/**
 * create 我的会员
 * Date: 2018/9/17 下午4:22
 */
public interface IJiuSuMineVipView extends IBaseView {

    //获取赠品记录
    void isListSuccess(List<YoCardReceiveListBean> data) ;
    void isFail(String msg);


}