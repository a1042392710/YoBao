package com.jjz.energy.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.Date;
import java.util.List;

import cn.jmessage.support.annotation.Nullable;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;

public class NoticeAdapter extends BaseRecycleNewAdapter<Conversation> {



        public NoticeAdapter(int layoutResId, @Nullable List<Conversation> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Conversation item) {
            //头像
            ImageView imgHead = helper.getView(R.id.item_img_head);
            //昵称
            TextView nickName = helper.getView(R.id.item_tv_notice_title);
            //聊天内容
            TextView imContent = helper.getView(R.id.item_tv_notice_content);
            //聊天时间
            TextView imTime = helper.getView(R.id.item_tv_notice_time);
            //聊天未读数量
            TextView imNoticeSum = helper.getView(R.id.item_tv_notice_sum);
            imTime.setText(StringUtil.getTimeFormatText(new Date(item.getLastMsgDate())));
            //有头像就加载头像
            if (null!=item.getAvatarFile()){
                GlideUtils.loadCircleImage(mContext, item.getAvatarFile().getAbsolutePath(),
                        imgHead);
            }
            nickName.setText(item.getTitle());
            //聊天未读 大与0才显示
            imNoticeSum.setVisibility(item.getUnReadMsgCnt() > 0 ? View.VISIBLE : View.GONE);
            imNoticeSum.setText(String.valueOf(item.getUnReadMsgCnt()));

            switch (item.getLatestMessage().getContentType()) {
                case text:
                    //内容
                    TextContent textContent = (TextContent) item.getLatestMessage().getContent();
                    String text = textContent.getText();
                    imContent.setText(text);
                    break;
                //图片
                case image:
                    ImageContent imageContent = (ImageContent) item.getLatestMessage().getContent();
                    imContent.setText("发来了一张照片");
                    break;
                case prompt: //提示
                    //内容
                    PromptContent promptContent = (PromptContent) item.getLatestMessage().getContent();
                    String promptText = promptContent.getPromptText();
                    imContent.setText(promptText);
                    break;
            }
        }
    }