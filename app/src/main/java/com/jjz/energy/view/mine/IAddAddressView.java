package com.jjz.energy.view.mine;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.mine.AddressBean;

/**
 * @author chenhao 2018/9/30
 * @function  地址管理
 */
public interface IAddAddressView extends IBaseView {
    //添加地址
    void isAddSuccess(AddressBean data);
    void isFail(String msg,boolean isNetAndServiceError);
    //删除收货地址
    void isDeleteSuccess(AddressBean data);
}
