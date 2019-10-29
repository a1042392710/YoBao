package com.jjz.energy.view.mine;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.mine.MineLikeAndFansBean;

/**
 * create 我的关注和我的粉丝
 * Date: 2018/9/17 下午4:22
 */
public interface IMineLikeAndFansView extends IBaseView {


    //获取我的粉丝
    default  void isGetMineFansSuc(MineLikeAndFansBean data){

    };
    //获取我的关注
    default  void isGetMineLikeSuc(MineLikeAndFansBean data){

    };

    //关注或取消关注
    void isFocusUserSuccess(String data);

    void isFail(String msg, boolean isNetAndServiceError);


}