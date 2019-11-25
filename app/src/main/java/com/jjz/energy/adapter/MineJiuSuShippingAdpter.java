package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingBean;

import java.util.List;

/**
 * @Features: 久速店内消费记录
 * @author: create by chenhao on 2019/11/25
 */
public class MineJiuSuShippingAdpter extends BaseRecycleNewAdapter<JiuSuShoppingBean.ListBean> {

    public MineJiuSuShippingAdpter(int layoutResId, @Nullable List<JiuSuShoppingBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JiuSuShoppingBean.ListBean item) {

    }
}
