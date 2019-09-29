package com.jjz.energy.ui.notice;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.PopWindowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;

/**
 * @Features: 消息
 * @author: create by chenhao on 2019/7/23
 */
public class NoticeFragment extends BaseLazyFragment {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_notice)
    RecyclerView rvNotice;
    /**
     * 消息适配器
     */
    private  NoticeAdapter mNoticeAdapter;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("消息");
        llToolbarLeft.setVisibility(View.INVISIBLE);
        initRv();
        //注册聊天事件监听
        initJMessage();
        initConversationList();
    }

    /**
     * 初始化极光IM
     */
    private void initJMessage() {
        UserInfo mUserInfo = UserLoginBiz.getInstance(mContext).readUserInfo();
        //开启全局监听事件
        JMessageClient.registerEventReceiver(this);
        //进入会话状态,不接收通知栏
        JMessageClient.enterSingleConversation(mUserInfo.getMobile());
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        rvNotice.setLayoutManager(new LinearLayoutManager(mContext));
        mNoticeAdapter = new NoticeAdapter(R.layout.item_notice,new ArrayList<>());
        rvNotice.setAdapter(mNoticeAdapter);
        //消息点击进聊天
        mNoticeAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext,IMActivity.class).putExtra("position",position));
        });
        //长按子项删除指定聊天记录
        mNoticeAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            PopWindowUtil.getInstance().showPopupWindow(mContext, "是否删除这条聊天记录", () -> {
                cn.jpush.im.android.api.model.UserInfo info = (cn.jpush.im.android.api.model.UserInfo) mNoticeAdapter.getData().get(position).getTargetInfo();
                JMessageClient.deleteSingleConversation(info.getUserName(), null);
            });
            return false;
        });
    }

    /**
     * 初始化聊天消息列表
     */
    private void initConversationList() {
        showLoading();
        //获取消息列表
        List<Conversation> mNoticeList = JMessageClient.getConversationList();
        if (mNoticeAdapter!=null) {
            stopLoading();
            if (!StringUtil.isListEmpty(mNoticeList)){
                mNoticeAdapter.setNewData(mNoticeList);
            }else{
                mNoticeAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_notice,"您还没有消息",false,null));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //重新获取消息列表
//        initConversationList();
    }

    // 接收消息 (主线程)(刷新UI)
    public void onEventMainThread(MessageEvent event){
      initConversationList();
    }

    /**
     * 消息
     */
    class NoticeAdapter extends BaseRecycleNewAdapter<Conversation>{

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
            imTime.setText(StringUtil.longToDate(item.getLastMsgDate(),"MM月dd日 HH:mm"));
            //有头像就加载头像
            if (null!=item.getAvatarFile()){
                GlideUtils.loadCircleImage(mContext,item.getAvatarFile().getAbsolutePath(),imgHead);
            }
            nickName.setText(item.getTitle());

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

    @Override
    public void onDestroy() {
        //退出会话界面 (开始接收通知栏)
        JMessageClient.exitConversation();
        //解绑聊天事件
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }



    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }
    
}
