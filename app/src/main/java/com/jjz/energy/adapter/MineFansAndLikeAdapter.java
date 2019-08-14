package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.List;

/**
 * @Features: 我的粉丝 我关注的 列表适配器
 * @author: create by chenhao on 2019/8/14
 */
public class MineFansAndLikeAdapter extends BaseRecycleNewAdapter<String> {

    public MineFansAndLikeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
