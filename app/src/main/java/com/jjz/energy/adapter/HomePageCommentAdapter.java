package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.ui.ImagePagerActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class HomePageCommentAdapter extends BaseRecycleNewAdapter<HomePageCommentBean.ListBean> {

        public HomePageCommentAdapter(int layoutResId, @Nullable List<HomePageCommentBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomePageCommentBean.ListBean item) {
            //用户头像
            ImageView item_img_my_head = helper.getView(R.id.item_img_my_head);
            GlideUtils.loadCircleImage(mContext,item.getHead_pic(),item_img_my_head);
            //评价等级
            ImageView item_img_my_evaluate_mark = helper.getView(R.id.item_img_my_evaluate_mark);
            //评价
            setStart(item_img_my_evaluate_mark,item.getStart());
            //评价图片
            ImageView item_img_my_evaluate = helper.getView(R.id.item_img_my_evaluate);
            //有图显示
            if (!StringUtil.isEmpty(item.getImg())){
                item_img_my_evaluate.setVisibility(View.VISIBLE);
                GlideUtils.loadRoundCircleImage(mContext,item.getImg(),item_img_my_evaluate);
                //查看大图
                item_img_my_evaluate.setOnClickListener(v -> {
                    List<String> list = new ArrayList<>();
                    list.add(item.getImg());
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(v.getMeasuredWidth(),
                            v.getMeasuredHeight());
                    ImagePagerActivity.startImagePagerActivity(BaseApplication.AppContext, list, 0, imageSize);

                });
            }else{
                item_img_my_evaluate.setVisibility(View.GONE);
            }
            //名称
            helper.setText(R.id.item_tv_my_name, item.getNickname());
            //内容
            helper.setText(R.id.item_tv_my_evaluate, item.getContent());
            //时间
            helper.setText(R.id.item_tv_my_evaluate_time, DateUtil.longToDate(item.getAdd_time(),null));
        }


    private void setStart(ImageView type, int start) {

        switch (start) {
            //差
            case 1:
                type.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_rb_bad_checked));
                break;
            //一般
            case 2:
                type.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_rb_ordinary_checked));
                break;
            //好
            case 3:
                type.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_rb_good_checked));
                break;
        }
    }

}