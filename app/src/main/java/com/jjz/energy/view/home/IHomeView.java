package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.HomeDetailBean;

/**
 * @Features: 首页
 * @author: create by chenhao on 2019/10/9
 */
public interface IHomeView extends IBaseView {


    //获取分类
    void isGetClassificationSuc(HomeDetailBean data);

    void isFail(String msg, boolean isNetAndServiceError);
}
