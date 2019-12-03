package com.jjz.energy.view.mine;


import com.jjz.energy.base.IBaseView;

/**
 * @author chenhao 2018/9/30
 * @function  意见反馈
 */
public interface IFeedBackView extends IBaseView {
    //提交意见反馈
    void isSuccess(String data);
    void isFail(String msg);




}
