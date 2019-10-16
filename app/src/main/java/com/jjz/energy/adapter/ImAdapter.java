package com.jjz.energy.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.ui.notice.IMActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.SafeClickListener;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Message;

/**
 * 用途: 极光聊天页面Adapter
 */
public class ImAdapter extends BaseRecycleNewAdapter<Message> {

    /**
     * 聊天对象的UserName
     */
    private String userName;
    private IMActivity mIMActivity;

    private String user_id;



    public ImAdapter(int layoutResId, @Nullable List<Message> data,IMActivity context,String userName) {
        super(layoutResId, data);
        this.mIMActivity = context;
        this.userName = userName;
    }

    /**
     * 设置用户id
     */
    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        //自己的聊天内容
        TextView tvMyContent = helper.getView(R.id.item_im_details_content);
        //自己的权重挤压
        TextView tvTc = helper.getView(R.id.item_im_details_tc);
        //别人的权重挤压
        TextView tvTc1 = helper.getView(R.id.item_im_details_tc1);
        //自己发送的图片
        ImageView imgMyPhoto = helper.getView(R.id.item_im_details_img);
        //时间
        TextView tvMyTime = helper.getView(R.id.item_im_details_time);
        //我的头像
        ImageView imgMyHead = helper.getView(R.id.item_im_details_my_head);
        //别人的头像
        ImageView imgHerHead = helper.getView(R.id.item_im_details_her_head);
        //发送失败的图片
        ImageView imgFail = helper.getView(R.id.item_img_fail);
        //进度条
        ProgressBar progressBar = helper.getView(R.id.item_progress);

        //展示聊天信息    对方的
        if (item.getFromUser() != null && item.getFromUser().getUserName().equals(userName)) {
            //对方的聊天内容，隐藏进度和失败图标
            progressBar.setVisibility(View.GONE);
            imgFail.setVisibility(View.GONE);
            //显示对方的头像
            imgHerHead.setVisibility(View.VISIBLE);
            //隐藏自己的头像
            imgMyHead.setVisibility(View.GONE);
            //对方的内容背景
            tvMyContent.setBackground(mContext.getResources().getDrawable(R.drawable.bg_white_radius));
            tvMyContent.setTextColor(mContext.getResources().getColor(R.color.text_black66));
            tvTc.setVisibility(View.GONE);
            tvTc1.setVisibility(View.VISIBLE);
        } else {
            //显示自己头像   自己的
            imgMyHead.setVisibility(View.VISIBLE);
            //隐藏对方头像
            imgHerHead.setVisibility(View.GONE);
            //内容背景
            tvMyContent.setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue_radius));
            tvMyContent.setTextColor(mContext.getResources().getColor(R.color.white));
            tvTc.setVisibility(View.VISIBLE);//权重挤压
            tvTc1.setVisibility(View.GONE);

            //自己的消息 发送成功或者失败
            switch (item.getStatus()) {
                //发送中  显示进度
                case send_going:
                    progressBar.setVisibility(View.VISIBLE);
                    imgFail.setVisibility(View.GONE);
                    break;
                //发送成功 关闭进度
                case send_success:
                    progressBar.setVisibility(View.GONE);
                    imgFail.setVisibility(View.GONE);
                    break;
                //发送失败  显示失败图片
                case send_fail:
                    progressBar.setVisibility(View.GONE);
                    imgFail.setVisibility(View.VISIBLE);
                    break;
            }

        }

        //加载对方头像
        GlideUtils.loadCircleImage(mContext, null != item.getFromUser().getAvatarFile() ?
                        item.getFromUser().getAvatarFile().getAbsolutePath() : "",
                imgHerHead);
        //加载我的头像
        GlideUtils.loadCircleImage(mContext, null != item.getFromUser().getAvatarFile() ?
                        item.getFromUser().getAvatarFile().getAbsolutePath() : "",
                imgMyHead);

        //与上一条消息做比较，五分钟以内的消息都合并到一起，隐藏时间
        if (helper.getLayoutPosition() != 0) {
            long s =
                    (item.getCreateTime() - mData.get(helper.getLayoutPosition() - 1).getCreateTime()) / (1000 * 60);
            if (s < 5) {
                tvMyTime.setVisibility(View.GONE);
            } else {
                tvMyTime.setVisibility(View.VISIBLE);
            }
        }
        //聊天时间
        tvMyTime.setText(DateUtil.stampToDateMinite(item.getCreateTime(), "yyyy年MM月dd日 HH:mm"));

        switch (item.getContentType()) {
            case text:
                tvMyContent.setVisibility(View.VISIBLE);
//                    tvMyTime.setVisibility(View.GONE);
                imgMyPhoto.setVisibility(View.GONE);
                //内容
                TextContent textContent = (TextContent) item.getContent();
                String text = textContent.getText();
                tvMyContent.setText(text);
                break;
            //图片
            case image:
                tvMyContent.setVisibility(View.GONE);
//                    tvMyTime.setVisibility(View.GONE);
                imgMyPhoto.setVisibility(View.VISIBLE);
                ImageContent imageContent = (ImageContent) item.getContent();
                //加载圆角图片
                if (imageContent.getLocalThumbnailPath() != null) {
                    GlideUtils.loadRoundCircleImage(mContext,
                            imageContent.getLocalThumbnailPath(), imgMyPhoto);
                }
                break;
//                case prompt: //提示
//                    tvMyContent.setVisibility(View.GONE);
//                    tvMyTime.setVisibility(View.GONE);
//                    imgMyPhoto.setVisibility(View.GONE);
//                    //内容
//                    PromptContent promptContent = (PromptContent) item.getContent();
//                    String promptText = promptContent.getPromptText();
//                    tvMyTime.setText(promptText);
//                    break;

        }

        //点头像进入主页
        if (!StringUtil.isEmpty(user_id)){
            imgHerHead.setOnClickListener(v -> mContext.startActivity(new Intent(mContext,
                    HomePageActivity.class).putExtra("user_id",Integer.valueOf(user_id))));
        }


        //重新发送消息  TODO 后面考虑发送图片的重试机制
        imgFail.setOnClickListener(new SafeClickListener() {
            @Override
            protected void onSingleClick(View v) {
                mIMActivity.sendMsgAgan(helper.getLayoutPosition());
            }
        });


    }


}
 
