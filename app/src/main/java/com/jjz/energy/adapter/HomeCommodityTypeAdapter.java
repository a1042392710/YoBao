package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.base.BaseRecycleNewAdapter;

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

    }
}
