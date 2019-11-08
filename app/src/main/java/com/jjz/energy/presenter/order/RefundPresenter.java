package com.jjz.energy.presenter.order;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.entry.mine.RefundHistroyBean;
import com.jjz.energy.entry.order.RefundDetailsBean;
import com.jjz.energy.model.order.RefundModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.order.IRefundView;

import java.io.File;
import java.util.List;

/**
 * @author chenhao 2018/9/20
 * @function 退款等流程
 */
public class RefundPresenter extends BasePresenter<RefundModel, IRefundView> {


    public RefundPresenter(IRefundView view) {
        initPresenter(view);
    }


    /**
     * 获取售后详情
     */
    public void getRefundDetails(String pack_no) {

        addSubscribe( mModel.getRefundDetails(pack_no)
                .subscribeWith(new CommonSubscriber<RefundDetailsBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(RefundDetailsBean response) {
                        mView.isGetRefundDetailsSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 买家撤销申请
     */
    public void buyerCancelApplication(String pack_no) {

        addSubscribe( mModel.buyerCancelApplication(pack_no)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isBuyerCancelApplicationSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 买家提交退货物流信息
     */
    public void buyerPutExpressInfo(String pack_no, List<File> files) {

        addSubscribe( mModel.buyerPutExpressInfo(pack_no,files)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isBuyerPutExpressInfoSuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));
    }


    /**
     * 卖家拒绝退款  填写拒绝理由
     */
    public void sellerRefuseApplication(String pack_no, List<File> files) {

        addSubscribe( mModel.sellerRefuseApplication(pack_no,files)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isSellerRefuseReturnMoneySuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));
    }

    /**
     * 卖家同意退款
     */
    public void sellerAgreeReturnMoney(String pack_no) {

        addSubscribe( mModel.sellerAgreeReturnMoney(pack_no)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isSellerAgreeReturnMoneySuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));
    }

    /**
     * 卖家同意退货，发送退货地址
     */
    public void sellerPutExpressInfo(String pack_no) {

        addSubscribe( mModel.sellerPutExpressInfo(pack_no)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isSellerPutExpressInfoSuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 获取收货地址
     */
    public void getAddressList(String pack_no) {

        addSubscribe( mModel.getAddressList(pack_no)
                .subscribeWith(new CommonSubscriber<AddressBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(AddressBean response) {
                        mView.isSellerGetAddressSuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取协商历史
     */
    public void getRefundHistoryList(String pack_no) {

        addSubscribe( mModel.getRefundHistoryList(pack_no)
                .subscribeWith(new CommonSubscriber<RefundHistroyBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(RefundHistroyBean response) {
                        mView.isGetHistorySuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    @Override
    protected RefundModel createModel() {
        return new RefundModel();
    }

}
