package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;

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
        ImageView imgHead = helper.getView(R.id.item_img_head);
        GlideUtils.loadCircleImage(mContext,"http://b-ssl.duitang.com/uploads/item/201711/10/20171110225150_ym2jw.jpeg",imgHead);
    }
}
