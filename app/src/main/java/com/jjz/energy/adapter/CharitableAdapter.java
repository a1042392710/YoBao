package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.List;

/**
 * @Features: 公益慈善
 * @author: create by chenhao on 2019/6/18
 */
public class CharitableAdapter extends BaseRecycleNewAdapter<String> {
    public CharitableAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
