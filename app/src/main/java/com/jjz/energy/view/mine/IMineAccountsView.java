package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.MineAccountBean;

/**
 * create 我的收款账户
 * Date: 2018/9/17 下午4:22
 */
public interface IMineAccountsView extends IBaseView {

    //获取收款账户信息
    void isGetInfoSuccess(MineAccountBean data);

    //设置默认收款账户
    void isPutAccountSuccess(String data);

    void isFail(String msg, boolean isNetAndServiceError);


}