package com.jjz.energy.presenter.mine;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.model.mine.AddressManagerModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IAddressManagerView;

/**
 * @author chenhao 2018/9/20
 * @function 地址管理
 */
public class AddressManagerPresenter extends BasePresenter<AddressManagerModel, IAddressManagerView> {


    public AddressManagerPresenter(IAddressManagerView view) {
        initPresenter(view);
    }


    /**
     * 获取收货地址列表
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
                        mView.stopLoading();
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 删除收货地址
     */
    public void deleteAddress(String pack_no) {

        addSubscribe( mModel.deleteAddress(pack_no)
                .subscribeWith(new CommonSubscriber<AddressBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(AddressBean response) {
                        mView.stopLoading();
                        mView.isDeleteSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 设为默认
     */
    public void defaultAddress(String pack_no) {

        addSubscribe( mModel.defaultAddress(pack_no)
                .subscribeWith(new CommonSubscriber<AddressBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(AddressBean response) {
                        mView.stopLoading();
                        mView.isDefaultSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }
    @Override
    protected AddressManagerModel createModel() {
        return new AddressManagerModel();
    }

}
