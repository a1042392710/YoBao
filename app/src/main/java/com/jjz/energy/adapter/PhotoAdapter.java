package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

public class PhotoAdapter extends BaseRecycleNewAdapter<String> {

        public PhotoAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imgPhoto = helper.getView(R.id.item_img_photo);
            GlideUtils.loadRoundCircleImage(mContext,item,imgPhoto);
            //防止图片闪烁
            if (!item.equals(imgPhoto.getTag(R.id.item_img_commodity))) {
                //加载商品图片
                Glide.with(mContext).asBitmap().load(item)
                        .apply(new RequestOptions().placeholder(R.color.color_primary_f5)).into(imgPhoto);
                imgPhoto.setTag(R.id.item_img_commodity, item);
            }
        }
    }