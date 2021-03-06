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
import com.jjz.energy.entry.mine.LikeGoodsBean;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.glide.RoundedCornersTransform;

import java.util.List;

/**
 * @Features: 首页 > 商品网格布局
 * @author: create by chenhao on 2019/6/18
 */
public class SearchGoodsAdapter extends BaseRecycleNewAdapter<LikeGoodsBean.ListBean> {


    public SearchGoodsAdapter(int layoutResId, @Nullable List<LikeGoodsBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeGoodsBean.ListBean item) {
        //商品图片
        ImageView imgGoods = helper.getView(R.id.item_img_commodity);
        //商品标题
        TextView tvGoodsTitle = helper.getView(R.id.item_tv_commodity_title);
        //卖家头像
        ImageView imgHead = helper.getView(R.id.item_img_head);
        //卖家名称
        TextView tvName = helper.getView(R.id.item_tv_name);
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
        //是否打折
        ImageView item_img_integral = helper.getView(R.id.item_img_integral);

        // 判断该商品 是否有折扣
        item_img_integral.setVisibility(item.getRebate() < 1 && item.getRebate()!=0 ? View.VISIBLE:View.GONE);
        //加载用户头像
        GlideUtils.loadCircleImage(mContext,item.getHead_pic(),imgHead);
        tvGoodsTitle.setText(item.getGoods_name());
        tvGoodsPrice.setText("￥"+item.getShop_price());
        tvName.setText(item.getNickname());
        tvWantNum.setVisibility(item.getCollect_sum()==0? View.GONE:View.VISIBLE);
        tvWantNum.setText(item.getCollect_sum()+"人想要");



    }
}
