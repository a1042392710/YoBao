package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.commodity.GoodsBean;
import com.jjz.energy.entry.commodity.GoodsListBean;
import com.jjz.energy.util.glide.RoundedCornersTransform;

import java.util.List;

/**
 * @Features: 首页 > 个人主页 网格布局
 * @author: create by chenhao on 2019/6/18
 */
public class HomePageCommodityAdapter extends BaseRecycleNewAdapter<GoodsBean> {


    public HomePageCommodityAdapter(int layoutResId, @Nullable List<GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        //商品图片
        ImageView imgGoods = helper.getView(R.id.item_img_commodity);
        //商品标题
        TextView tvGoodsTitle = helper.getView(R.id.item_tv_commodity_title);
        //商品价格
        TextView tvGoodsPrice = helper.getView(R.id.item_tv_commodity_money);
        //多少人想要
        TextView tvWantNum = helper.getView(R.id.item_tv_want_num);

        //指定上方圆角
        RoundedCornersTransform transform = new RoundedCornersTransform(mContext,8f);
        transform.setNeedCorner(true, true, false, false);
        //防止图片闪烁
        if (!item.getGoods_images().equals(imgGoods.getTag(R.id.item_img_commodity))) {
            //加载商品图片
            Glide.with(mContext).asBitmap().load(item.getGoods_images())
                    .apply(new RequestOptions().placeholder(R.color.color_primary_f5).transform(transform)).into(imgGoods);
         imgGoods.setTag(R.id.item_img_commodity, item.getGoods_images());
        }
        tvGoodsTitle.setText(item.getGoods_name());
        tvGoodsPrice.setText("￥"+item.getShop_price());
        tvWantNum.setVisibility(item.getCollect_sum()==0? View.GONE:View.VISIBLE);
        tvWantNum.setText(item.getCollect_sum()+"人想要");



    }
}
