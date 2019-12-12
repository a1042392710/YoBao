package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.CommissionDetailBean;
import com.jjz.energy.entry.jiusu.MineWalletBean;
import com.jjz.energy.entry.jiusu.MineWalletListBean;
import com.jjz.energy.entry.jiusu.WithdrawInfoBean;
import com.jjz.energy.entry.jiusu.WithdrawListBean;
import com.jjz.energy.model.jiusu.MineWalletModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IMineWalletView;

import java.util.List;

/**
 * create by: 我的钱包
 * Date: 2018/9/17 下午4:22
 */
public class MineWalletPresenter extends BasePresenter<MineWalletModel, IMineWalletView> {


    public MineWalletPresenter(IMineWalletView view) {
        initPresenter(view);
    }


    /**
     * 查询余额
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getBalance(String map) {

        addSubscribe(mModel.getBalance(map)
                .subscribeWith(new CommonSubscriber<MineWalletBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(MineWalletBean response) {
                        mView.stopLoading();
                        mView.isBalanceSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 查询上次提现信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getWithdrawInfo(String map) {

        addSubscribe(mModel.getWithdrawInfo(map)
                .subscribeWith(new CommonSubscriber<WithdrawInfoBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(WithdrawInfoBean response) {
                        mView.stopLoading();
                        mView.isGetWithdrawSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 查询佣金列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getBalanceList(String map, boolean isShowLoading) {

        addSubscribe(mModel.getBalanceList(map)
                .subscribeWith(new CommonSubscriber<List<MineWalletListBean>>() {

                    @Override
                    protected void startLoading() {
                        if (isShowLoading){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(List<MineWalletListBean> response) {
                        mView.stopLoading();
                        mView.isBalanceListSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 提交提现信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void putWithdrawInfo(String map) {

        addSubscribe(mModel.putWithdrawInfo(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutWithdrawSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取提现列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getWithdrawList(String map) {

        addSubscribe(mModel.getWithdrawList(map)
                .subscribeWith(new CommonSubscriber<WithdrawListBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(WithdrawListBean response) {
                        mView.stopLoading();
                        mView.isWithdrawListSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取佣金详情列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getCommissionDetail(String map) {

        addSubscribe(mModel.getCommissionDetail(map)
                .subscribeWith(new CommonSubscriber<CommissionDetailBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(CommissionDetailBean response) {
                        mView.stopLoading();
                        mView.isGetCommissionDeatil(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected MineWalletModel createModel() {
        return new MineWalletModel();
    }

}