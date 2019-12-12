package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.CommissionDetailBean;
import com.jjz.energy.entry.jiusu.MineWalletBean;
import com.jjz.energy.entry.jiusu.MineWalletListBean;
import com.jjz.energy.entry.jiusu.WithdrawInfoBean;
import com.jjz.energy.entry.jiusu.WithdrawListBean;

import java.util.List;

/**
 * create 我的佣金
 * Date: 2018/9/17 下午4:22
 */
public interface IMineWalletView extends IBaseView {
    //查询余额成功
    default void isBalanceSuccess(MineWalletBean data) {

    }
    //查询佣金列表成功
    default void isBalanceListSuccess(List<MineWalletListBean> data) {

    }
    //提交提现信息
    default void isPutWithdrawSuccess(String data) {

    }
    //获取提现记录
    default void isWithdrawListSuccess(WithdrawListBean data) {

    }
    //获取佣金详情
    default void isGetCommissionDeatil(CommissionDetailBean data) {

    }

    //获取上次提现信息
    default void isGetWithdrawSuc(WithdrawInfoBean data) {

    }

    void isFail(String msg);


}