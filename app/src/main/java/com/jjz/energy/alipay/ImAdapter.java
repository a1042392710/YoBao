package com.jjz.energy.alipay;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Message;

/**
 *用途: 极光聊天页面Adapter
 */
public class ImAdapter  extends BaseRecycleNewAdapter<Message> {

    private ImageView mHead;
    private String userName;


    public ImAdapter(int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);
    }

    public  void  setUserName (String userName){
        this.userName = userName;
    }


    public void setData (List<Message> data){
        this.mData = data;
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
        if (item.getFromUser() != null) {
            //我是方小杰
            if (item.getFromUser().getUserName().equals(userName)) {
                //自己的头像
                mHead = helper.getView(R.id.item_im_details_head1);
                //显示自己头像
                mHead.setVisibility(View.VISIBLE);
                //隐藏对方头像
                helper.getView(R.id.item_im_details_head).setVisibility(View.GONE);
                //内容背景
                tvMyContent.setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue_radius));
                tvMyContent.setTextColor(mContext.getResources().getColor(R.color.white));
                tvTc.setVisibility(View.VISIBLE);//权重挤压
                tvTc1.setVisibility(View.GONE);

            } else {
                //对象头像
                mHead = helper.getView(R.id.item_im_details_head);
                //显示对方的头像
                mHead.setVisibility(View.VISIBLE);
                //隐藏自己的头像
                helper.getView(R.id.item_im_details_head1).setVisibility(View.GONE);
                //对方的内容背景
                tvMyContent.setBackground(mContext.getResources().getDrawable(R.drawable.bg_white_radius));
                tvMyContent.setTextColor(mContext.getResources().getColor(R.color.text_blackA6));
                tvTc.setVisibility(View.GONE);
                tvTc1.setVisibility(View.VISIBLE);
            }
            //展示头像
            item.getFromUser().getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int i, String s, Bitmap bitmap) {
                    if (bitmap != null) {
                        GlideUtils.loadCircleImage(mContext,bitmap,mHead);
                    } else {
                        LogUtil.e("极光会话详情-用户头像赋值", s);
                    }
                }
            });

            switch (item.getContentType()) {
                case text:
                    tvMyContent.setVisibility(View.VISIBLE);
                    tvMyTime.setVisibility(View.GONE);
                    imgMyPhoto.setVisibility(View.GONE);
                    //内容
                    TextContent textContent = (TextContent) item.getContent();
                    String text = textContent.getText();
                    tvMyContent.setText(text);
                    break;
                    //图片
                case image:
                    tvMyContent.setVisibility(View.GONE);
                    tvMyTime.setVisibility(View.GONE);
                    imgMyPhoto.setVisibility(View.VISIBLE);
                    ImageContent imageContent = (ImageContent) item.getContent();
                    //加载圆角图片
                    if (imageContent.getLocalThumbnailPath() != null) {
                        GlideUtils.loadRoundCircleImage(mContext,imageContent.getLocalThumbnailPath(),imgMyPhoto);
                    }
                    break;
                case prompt: //提示
                    tvMyContent.setVisibility(View.GONE);
                    tvMyTime.setVisibility(View.VISIBLE);
                    imgMyPhoto.setVisibility(View.GONE);
                    //内容
                    PromptContent promptContent = (PromptContent) item.getContent();
                    String promptText = promptContent.getPromptText();
                    tvMyTime.setText(promptText);
                    break;

            }
        }

    }
}
 
