package com.jjz.energy.view;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.BindOwnerInfoBean;

/**
 * create 车主信息
 * Date: 2018/9/17 下午4:22
 */
public interface IBindOwnerInfoView extends IBaseView {
    //绑定成功
    void isPutSuccess(String data);
    //获取数据成功
    void isGetSuccess(BindOwnerInfoBean data);

    void isFail(String msg);


}