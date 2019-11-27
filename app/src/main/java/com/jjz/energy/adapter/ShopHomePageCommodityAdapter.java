package com.jjz.energy.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.commodity.ShopCommodityListBean;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

public class ShopHomePageCommodityAdapter extends BaseRecycleNewAdapter<ShopCommodityListBean> {


    public ShopHomePageCommodityAdapter(int layoutResId, @Nullable List<ShopCommodityListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCommodityListBean item) {
        //给原价加上中划线
        TextView item_tv_old_money = helper.getView(R.id.item_tv_old_money);
        //多少人想要
        TextView tvWantNum = helper.getView(R.id.item_tv_want_num);

        item_tv_old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        item_tv_old_money.getPaint().setAntiAlias(true); //去掉锯齿
        item_tv_old_money.setText("￥" + item.getMarket_price());
        //现价
        helper.setText(R.id.item_tv_new_money, "￥" + item.getShop_price());
        //标题
        helper.setText(R.id.item_tv_commodity_title, item.getGoods_name());
        //折扣
        helper.setText(R.id.item_tv_toast, "积分享" + (item.getRebate() * 10) + "折");
        //商品图片
        ImageView item_img_commodity = helper.getView(R.id.item_img_commodity);
        GlideUtils.loadRoundCircleImage(mContext, item.getGoods_images(), item_img_commodity);
        //多少人想要
        tvWantNum.setVisibility(item.getCollect_sum() == 0 ? View.GONE : View.VISIBLE);
        tvWantNum.setText(item.getCollect_sum() + "人想要");
    }
}