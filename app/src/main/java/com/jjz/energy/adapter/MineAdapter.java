package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.MineBean;

import java.util.List;

public class MineAdapter extends BaseRecycleNewAdapter<MineBean> {

        public MineAdapter(int layoutResId, @Nullable List<MineBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineBean item) {
         ImageView img = helper.getView(R.id.item_img_photo);
         TextView tv = helper.getView(R.id.item_tv_title);
         img.setImageResource(item.getImg());
            tv.setText(item.getTitle());

        }
    }