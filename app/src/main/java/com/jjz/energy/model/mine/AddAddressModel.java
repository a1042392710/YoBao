package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.AddressBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @author chenhao 2018/11/27
 * @function 地址添加/删除
 */
public class AddAddressModel extends BaseModel {

    /**
     * 添加地址
     * @param requestData
     */

    public Flowable<AddressBean> addAddress(String requestData) {
        return  RetrofitFactory.getRetrofit().create(Api.class).addAddress(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    /**
     * 删除地址
     * @param requestData
     */

    public Flowable<AddressBean> deleteAddress(String requestData) {

        return  RetrofitFactory.getRetrofit().create(Api.class).deleteAddress(requestData).compose(RxSchedulerHepler.handleMyResult());

    }


}
