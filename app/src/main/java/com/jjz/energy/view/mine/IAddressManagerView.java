package com.jjz.energy.view.mine;


import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.AddressBean;

/**
 * @author chenhao 2018/9/30
 * @function  地址管理
 */
public interface IAddressManagerView extends IBaseView {
    //获取地址列表
    void isSuccess(AddressBean data);
    void isFail(String msg);

    //删除地址
    void isDeleteSuccess(AddressBean data);
    //设为默认
    void isDefaultSuccess(AddressBean data);
}
