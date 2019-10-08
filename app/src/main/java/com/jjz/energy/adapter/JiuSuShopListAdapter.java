package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

/**
 * @Features: 商户列表
 * @author: create by chenhao on 2019/10/8
 */
public class JiuSuShopListAdapter extends BaseRecycleNewAdapter<String> {

    public JiuSuShopListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //商户图片
        ImageView itemImgShop = helper.getView(R.id.item_img_shop);
        //商户名称
        TextView itemTvShopName = helper.getView(R.id.item_tv_shop_name);
        //商户评分
        RatingBar itemRatingbar = helper.getView(R.id.item_ratingbar);
        //人均消费
        TextView itemTvConsume = helper.getView(R.id.item_tv_consume);
        //商户与你的距离
        TextView itemTvDistance = helper.getView(R.id.item_tv_distance);
        //所在区域与商户类型
        TextView itemTvAreaAndType = helper.getView(R.id.item_tv_area_and_type);
        GlideUtils.loadRoundCircleImage(mContext,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570528215677&di=18f480c83a3558750cc7168e64d42c39&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110619%2F1626751_151450468356_2.jpg",itemImgShop);


    }
}
