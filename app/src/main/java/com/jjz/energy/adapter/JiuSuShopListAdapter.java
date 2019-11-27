package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.system.SpUtil;

import java.util.List;

/**
 * @Features: 商户列表
 * @author: create by chenhao on 2019/10/8
 */
public class JiuSuShopListAdapter extends BaseRecycleNewAdapter<JiuSuShopBean.ListBean> {

    private double mLng,mLat;

    public JiuSuShopListAdapter(int layoutResId, @Nullable List<JiuSuShopBean.ListBean> data) {
        super(layoutResId, data);
        String lng =SpUtil.init(mContext).readString(Constant.LOCATION_LNG);
        String lat = SpUtil.init(mContext).readString(Constant.LOCATION_LAT);

       mLng =  StringUtil.isEmpty(lng)?0 :Double.valueOf(lng);
       mLat =  StringUtil.isEmpty(lat)?0 :Double.valueOf(lat);
    }

    @Override
    protected void convert(BaseViewHolder helper, JiuSuShopBean.ListBean item) {
        //商户图片
        ImageView itemImgShop = helper.getView(R.id.item_img_shop);
        //商户名称
        TextView itemTvShopName = helper.getView(R.id.item_tv_shop_name);
        //商户好评率
        TextView itemTvFavorableRate = helper.getView(R.id.item_tv_favorable_rate);
        //人均消费
        TextView itemTvConsume = helper.getView(R.id.item_tv_consume);
        //商户与你的距离
        TextView itemTvDistance = helper.getView(R.id.item_tv_distance);
        //所在区域与商户类型
        TextView itemTvAreaAndType = helper.getView(R.id.item_tv_area_and_type);
        //防止图片闪烁
        if (!item.getShop_img().equals(itemImgShop.getTag(R.id.item_img_commodity))) {
            GlideUtils.loadRoundCircleImage(mContext,item.getShop_img(),itemImgShop);
            itemImgShop.setTag(R.id.item_img_commodity, item.getShop_img());
        }
        itemTvShopName.setText(item.getShop_name());
        itemTvFavorableRate.setText(item.getApplause_rate()+"%好评率");
        itemTvConsume.setText(item.getAvg_tax()+"元/人");
        itemTvAreaAndType.setText(item.getDistrict()+" | "+ item.getCateName());
        itemTvDistance.setText(Utils.getDistance(mLng,mLat,item.getLng(),item.getLat())+"km");



    }
}
