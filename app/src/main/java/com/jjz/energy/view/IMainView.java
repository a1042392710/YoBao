package com.jjz.energy.view;


import com.jjz.energy.base.IBaseView;

/**
 * @author chenhao 2018/9/30
 * @function 首页
 */
public interface IMainView extends IBaseView {
    //提交推送RId
    void isSuccess(String data);
    void isFail(String msg);

}
