package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.MineInfoBean;

/**
 * create 我的页面
 * Date: 2018/9/17 下午4:22
 */
public interface IMineView extends IBaseView {

    //获取个人信息
    void isGetInfoSuccess(MineInfoBean data);

    void isFail(String msg, boolean isNetAndServiceError);


}