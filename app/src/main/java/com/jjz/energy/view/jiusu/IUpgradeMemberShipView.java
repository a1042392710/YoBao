package com.jjz.energy.view.jiusu;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu.VipListInfo;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import java.util.List;

/**
 * create 升级会员
 * Date: 2018/9/17 下午4:22
 */
public interface IUpgradeMemberShipView extends IBaseView {

    //升级会员
    default  void isUpVipSuccess(OrderPayTypeBean data){

    };
    //提交签名
    default  void isSubmitFileSuccess(OrderPayTypeBean data){

    };

    //获取会员信息
   default void isVipInfoSuccess(List<VipListInfo> data){

   };

    void isFail(String msg);


}