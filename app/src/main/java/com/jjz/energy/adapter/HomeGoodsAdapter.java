package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.GoodsBean;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.glide.RoundedCornersTransform;

import java.util.List;

/**
 * @Features: 首页 > 商品网格布局
 * @author: create by chenhao on 2019/6/18
 */
public class HomeGoodsAdapter extends BaseRecycleNewAdapter<GoodsBean> {


    public HomeGoodsAdapter(int layoutResId, @Nullable List<GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
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
        //加载商品图片
        Glide.with(mContext).asBitmap().load("http://img4.imgtn.bdimg.com/it/u=3200546096,2564818638&fm=26&gp=0.jpg")
                .apply(new RequestOptions().placeholder(R.color.color_primary_f5).transform(transform)).into(imgGoods);
        //加载用户头像
        GlideUtils.loadCircleImage(mContext,"http://b-ssl.duitang.com/uploads/item/201502/10/20150210223250_5dJeC.jpeg",imgHead);

        tvGoodsTitle.setText("商品标题");
        tvGoodsPrice.setText("￥"+"商品价格");
        tvName.setText("卖家名称");
        tvWantNum.setText("多少人想要");


    }
}
