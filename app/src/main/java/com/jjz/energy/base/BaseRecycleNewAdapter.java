package com.jjz.energy.base;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.util.StringUtil;

import java.util.List;

/**
 * @author chenhao 2018/12/12
 *  避免每次都 设置BaseViewHolder
 */
public abstract class BaseRecycleNewAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {


    public BaseRecycleNewAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }
    /**
     * 刷新数据
     *
     * @param data
     */
    public void notifyChangeData(List<T> data) {
        if (StringUtil.isListEmpty(data)) {
            mData.clear();
            notifyDataSetChanged();
            return;
        }
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
