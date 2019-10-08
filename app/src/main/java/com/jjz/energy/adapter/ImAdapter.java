package com.jjz.energy.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.ImMessageBean;
import com.jjz.energy.ui.home.HomePageActivity;
import com.jjz.energy.ui.notice.IMActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;

/**
 * 用途: 极光聊天页面Adapter
 */
public class ImAdapter extends BaseRecycleNewAdapter<ImMessageBean> {

    private String userName;
    private IMActivity mIMActivity;



    public ImAdapter(int layoutResId, @Nullable List<ImMessageBean> data,IMActivity context) {
        super(layoutResId, data);
        this.mIMActivity = context;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    protected void convert(BaseViewHolder helper, ImMessageBean item) {
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
        //根据消息的状态来控制显示隐藏
        switch (item.getMessageStatus()){
            case ImMessageBean.SEND_MESSAGE_START:
                progressBar.setVisibility(View.VISIBLE);
                break;
            case ImMessageBean.SEND_MESSAGE_SUC:
                progressBar.setVisibility(View.GONE);
                break;
            case ImMessageBean.SEND_MESSAGE_FAIL:
                progressBar.setVisibility(View.GONE);
                imgFail.setVisibility(View.VISIBLE);
                break;
        }

        //展示聊天信息  自己的聊天信息
        if (item.getMessage().getFromUser() != null && item.getMessage().getFromUser().getUserName().equals(userName)) {
            //显示自己头像
            imgMyHead.setVisibility(View.VISIBLE);
            //隐藏对方头像
            imgHerHead.setVisibility(View.GONE);
            //内容背景
            tvMyContent.setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue_radius));
            tvMyContent.setTextColor(mContext.getResources().getColor(R.color.white));
            tvTc.setVisibility(View.VISIBLE);//权重挤压
            tvTc1.setVisibility(View.GONE);

        } else {
            //显示对方的头像
            imgHerHead.setVisibility(View.VISIBLE);
            //隐藏自己的头像
            imgMyHead.setVisibility(View.GONE);
            //对方的内容背景
            tvMyContent.setBackground(mContext.getResources().getDrawable(R.drawable.bg_white_radius));
            tvMyContent.setTextColor(mContext.getResources().getColor(R.color.text_black66));
            tvTc.setVisibility(View.GONE);
            tvTc1.setVisibility(View.VISIBLE);
        }

        //加载对方头像
        GlideUtils.loadCircleImage(mContext, null != item.getMessage().getFromUser().getAvatarFile() ?
                        item.getMessage().getFromUser().getAvatarFile().getAbsolutePath() : "",
                imgHerHead);
        //加载我的头像
        GlideUtils.loadCircleImage(mContext, null != item.getMessage().getFromUser().getAvatarFile() ?
                        item.getMessage().getFromUser().getAvatarFile().getAbsolutePath() : "",
                imgMyHead);

        //与上一条消息做比较，五分钟以内的消息都合并到一起，隐藏时间
        if (helper.getLayoutPosition() != 0) {
            long s =
                    (item.getMessage().getCreateTime() - mData.get(helper.getLayoutPosition() - 1).getMessage().getCreateTime()) / (1000 * 60);
            if (s < 5) {
                tvMyTime.setVisibility(View.GONE);
            } else {
                tvMyTime.setVisibility(View.VISIBLE);
            }
        }
        //聊天时间
        tvMyTime.setText(DateUtil.stampToDateMinite(item.getMessage().getCreateTime(), "yyyy年MM月dd日 HH:mm"));

        switch (item.getMessage().getContentType()) {
            case text:
                tvMyContent.setVisibility(View.VISIBLE);
//                    tvMyTime.setVisibility(View.GONE);
                imgMyPhoto.setVisibility(View.GONE);
                //内容
                TextContent textContent = (TextContent) item.getMessage().getContent();
                String text = textContent.getText();
                tvMyContent.setText(text);
                break;
            //图片
            case image:
                tvMyContent.setVisibility(View.GONE);
//                    tvMyTime.setVisibility(View.GONE);
                imgMyPhoto.setVisibility(View.VISIBLE);
                ImageContent imageContent = (ImageContent) item.getMessage().getContent();
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
        imgHerHead.setOnClickListener(v -> mContext.startActivity(new Intent(mContext,
                HomePageActivity.class)));
        imgMyHead.setOnClickListener(v -> mContext.startActivity(new Intent(mContext,
                HomePageActivity.class)));

        //重新发送消息  TODO 后面考虑发送图片的重试机制
        imgFail.setOnClickListener(v -> {
            mIMActivity.sendMsgAgan(((TextContent) item.getMessage().getContent()).getText(),helper.getLayoutPosition());
        });
        LogUtil.e("久速","消息发送状态"+item.getMessage().getStatus());
    }


}
 
