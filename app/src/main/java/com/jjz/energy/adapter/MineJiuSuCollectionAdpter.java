package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingBean;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

/**
 * @Features: 久速店内收款记录
 * @author: create by chenhao on 2019/11/25
 */
public class MineJiuSuCollectionAdpter extends BaseRecycleNewAdapter<JiuSuShoppingBean.ListBean> {

    public MineJiuSuCollectionAdpter(int layoutResId, @Nullable List<JiuSuShoppingBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JiuSuShoppingBean.ListBean item) {
        helper.setText(R.id.item_tv_shop_name,item.getNickname());
        helper.setText(R.id.item_tv_new_money,"实付："+item.getOrder_amount());
        helper.setText(R.id.item_tv_time, DateUtil.longToDate(item.getPay_time(),null));
        ImageView img = helper.getView(R.id.item_img_shop);
        GlideUtils.loadRoundCircleImage(mContext,item.getHead_pic(),img);
    }
}
