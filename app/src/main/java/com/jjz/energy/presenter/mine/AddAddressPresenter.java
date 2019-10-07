package com.jjz.energy.presenter.mine;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.AddressBean;
import com.jjz.energy.model.mine.AddAddressModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IAddAddressView;

/**
 * @author chenhao 2018/9/20
 * @function 地址添加/删除
 */
public class AddAddressPresenter extends BasePresenter<AddAddressModel, IAddAddressView> {


    public AddAddressPresenter(IAddAddressView view) {
        initPresenter(view);
    }


    /**
     * 添加和修改收货地址
     */
    public void addAndModifyAddress(String pack_no) {

        addSubscribe( mModel.addAddress(pack_no)
                .subscribeWith(new CommonSubscriber<AddressBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(AddressBean response) {
                        mView.stopLoading();
                        mView.isAddSuccess(response);
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

    @Override
    protected AddAddressModel createModel() {
        return new AddAddressModel();
    }

}
