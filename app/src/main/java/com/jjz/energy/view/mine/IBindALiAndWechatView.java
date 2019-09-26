package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.BindBean;

/**
 * create 绑定微信和支付宝
 * Date: 2018/9/17 下午4:22
 */
public interface IBindALiAndWechatView extends IBaseView {
    //绑定成功
    void isPutSuccess(String data);
    //获取数据成功
    void isGetSuccess(BindBean data);

    void isFail(String msg);


}