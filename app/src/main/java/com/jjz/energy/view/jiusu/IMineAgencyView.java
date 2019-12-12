package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.AgencyBean;

/**
 * create 查询下级代理信息
 * Date: 2018/9/17 下午4:22
 */
public interface IMineAgencyView extends IBaseView {
    //查询成功
    void isSuccess(AgencyBean data);

    void isFail(String msg);


}