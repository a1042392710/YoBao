package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.ShareInfoBean;

/**
 * create 分享推荐
 * Date: 2018/9/17 下午4:22
 */
public interface IShareView extends IBaseView {
    //分享推荐成功
    void isSuccess(ShareInfoBean data);

    void isFail(String msg);


}