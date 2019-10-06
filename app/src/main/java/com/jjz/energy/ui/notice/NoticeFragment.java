package com.jjz.energy.ui.notice;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.NoticeAdapter;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.PopWindowUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
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
        EventBus.getDefault().register(this);
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
        //进入单聊会话，不接受指定用户的通知
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
                //删除该条聊天
                mNoticeAdapter.remove(position);
                if (mNoticeAdapter.getData().size()==0){
                    mNoticeAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_notice,"您还没有消息",false,null));
                }
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


    // 接收消息 (主线程)(刷新UI)
    public void onEventMainThread(MessageEvent event){
      initConversationList();
    }

    /**
     * 刷新通知
     */
    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void refreshNotice(LoginEventBean loginEventBean){
        if (loginEventBean.getLoginStatus()==LoginEventBean.REFRESH_NOTICE){
            initConversationList();
        }
    }

    @Override
    public void onDestroy() {
        //退出会话界面 (开始接收通知栏)
        JMessageClient.exitConversation();
        //解绑聊天事件
        JMessageClient.unRegisterEventReceiver(this);
        //解绑
        EventBus.getDefault().unregister(this);
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
