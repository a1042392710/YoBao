package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.glide.RoundedCornersTransform;

import java.util.List;

/**
 * @Features: 首页 > 商品分类网格布局
 * @author: create by chenhao on 2019/6/18
 */
public class HomeCommodityTypeAdapter extends BaseRecycleNewAdapter<String> {
    public HomeCommodityTypeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imgPhoto = helper.getView(R.id.img_commodity);
        ImageView imgHead = helper.getView(R.id.img_head);
        //指定上方圆角
        RoundedCornersTransform transform = new RoundedCornersTransform(mContext,8f);
        transform.setNeedCorner(true, true, false, false);
        Glide.with(mContext).asBitmap().load("http://img4.imgtn.bdimg.com/it/u=3200546096,2564818638&fm=26&gp=0.jpg")
                .apply(new RequestOptions().placeholder(R.color.color_primary_f5).transform(transform)).into(imgPhoto);
        //头像
        GlideUtils.loadCircleImage(mContext,"http://b-ssl.duitang.com/uploads/item/201502/10/20150210223250_5dJeC.jpeg",imgHead);
    }
}
