package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @author ch 2018 12/20
 * @function: 地址列表
 */
public class AddressManagerModel extends BaseModel {

    //获取地址列表
    public Flowable<AddressBean> getAddressList(String requestData) {

        return RetrofitFactory.getRetrofit().create(Api.class).getAddressList(requestData).compose(RxSchedulerHepler.handleMyResult());

    }
    //删除地址
    public Flowable<AddressBean> deleteAddress(String requestData) {

        return RetrofitFactory.getRetrofit().create(Api.class).deleteAddress(requestData).compose(RxSchedulerHepler.handleMyResult());

    }

    //默认地址
    public Flowable<AddressBean> defaultAddress(String requestData) {

        return RetrofitFactory.getRetrofit().create(Api.class).defaultAddress(requestData).compose(RxSchedulerHepler.handleMyResult());

    }
}