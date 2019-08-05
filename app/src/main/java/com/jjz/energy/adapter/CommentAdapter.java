package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.List;

/**
 * @Features: 评论列表
 * @author: create by chenhao on 2019/8/5
 */
public class CommentAdapter  extends BaseRecycleNewAdapter<String> {
    public CommentAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
