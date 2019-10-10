package com.jjz.energy.ui.notice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.ImAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.ui.mine.shop_order.SureBuyActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.system.SoftKeyBoardListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * @Features: 聊天页面
 * @author: create by chenhao on 2019/9/22
 */
public class IMActivity extends BaseActivity {
    @BindView(R.id.img_im_back)
    ImageView imgImBack;
    @BindView(R.id.tv_im_title)
    TextView tvImTitle;
    @BindView(R.id.rv_im)
    RecyclerView rvIm;
    @BindView(R.id.et_im)
    EditText etIm;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.view_keybord)
    View view_keybord;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_commodity_price)
    TextView tvCommodityPrice;
    @BindView(R.id.tv_commodity_freight)
    TextView tvCommodityFreight;
    @BindView(R.id.tv_commodity_status)
    TextView tvCommodityStatus;
    @BindView(R.id.tv_commodity_state)
    TextView tvCommodityState;
    @BindView(R.id.ll_edit)
    LinearLayout llEdit;

    /**
     * 聊天的适配器
     */
    private ImAdapter mImAdapter;

    /**
     * 会话对象
     */
    private Conversation conversation;
    /**
     * 会话对象的UserName
     */
    private String userName;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_im;
    }

    @Override
    protected void initView() {
        //聊天对象的UserName 必传
        userName = getIntent().getStringExtra("userName");
        //展示商品图片
        GlideUtils.loadImage(mContext,"http://img004.hc360.cn/k2/M0F/4D/EE/wKhQxFmx8rCEEGuwAAAAAIeZn9k905.jpg",imgCommodity);
        initImRv();
        initEditHight();
        initImData();
    }

    /**
     * 初始化列表和Im聊天
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initImRv() {
        //让列表从底部开始加载
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvIm.setLayoutManager(linearLayoutManager);
        //绑定聊天数据
        mImAdapter = new ImAdapter(R.layout.item_im, new ArrayList<>(),this,userName);
        rvIm.setAdapter(mImAdapter);
        rvIm.setOnTouchListener((v, event) -> {
            //触摸列表的时候，缩回软键盘
            disMissSoftKeyboard();
            return false;
        });
        JMessageClient.registerEventReceiver(this);
        //进入会话状态,不接收通知栏
        JMessageClient.enterSingleConversation(userName);
    }


    //初始化聊天数据
    public void initImData() {
        //获取会话
        conversation = JMessageClient.getSingleConversation(userName);
        //重置会话聊天未读
        conversation.resetUnreadCount();
        //标题
        tvImTitle.setText(conversation.getTitle() == null ? "" : conversation.getTitle());
        //聊天对象
        UserInfo info = (UserInfo) conversation.getTargetInfo();
        userName = info.getUserName();
        //刷新聊天数据并且滚动到底部
        if (conversation.getAllMessage() != null) {
            notifyImList();
        }
    }

    /**
     * 刷新聊天数据
     */
    private void notifyImList(){
        if (conversation.getAllMessage().size() > 0) {
            //设置刷新不闪屏`
            ((SimpleItemAnimator) rvIm.getItemAnimator()).setSupportsChangeAnimations(false);
            //处理会话数据
            mImAdapter.setNewData(conversation.getAllMessage());
            rvIm.scrollToPosition(conversation.getAllMessage().size() - 1);
        }
    }

    /**
     * 接收在线消息
     **/
    public void onEventMainThread(MessageEvent event) {
        //全部设为已读
        conversation.resetUnreadCount();
        mImAdapter.addData(event.getMessage());
        rvIm.scrollToPosition(mImAdapter.getData().size()-1);
    }


    /**
     * 重新发送消息
     */
    public void sendMsgAgan (int position){
        Message msg = mImAdapter.getData().get(position);
        //发送消息
        JMessageClient.sendMessage(msg);
        //改变指定位置的消息并刷新
        mImAdapter.setData(position, conversation.getAllMessage().get(position));
        msg.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                //收到回调后刷新状态
                notifyImList();
            }
        });
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    private void sendMsg(String msg) {
        //创建消息
        Message message = JMessageClient.createSingleTextMessage(userName, null,
                msg);
        //发送消息
        JMessageClient.sendMessage(message);
        //刷新数据并获取焦点，使得软键盘不缩回
        etIm.setFocusable(true);
        etIm.setFocusableInTouchMode(true);
        etIm.setText("");
        //添加并刷新刚发送的消息
        mImAdapter.addData(message);
        rvIm.scrollToPosition(mImAdapter.getData().size()-1);
        //记录该条消息的下标
        int  position =  mImAdapter.getData().size()-1;
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
              //收到回调后刷新该条消息
                mImAdapter.setData(position,conversation.getAllMessage().get(position));
            }
        });

    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @Override
    protected void onDestroy() {
        //退出会话界面 (开始接收通知栏)
        JMessageClient.exitConversation();
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }


    //监听返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();//注释掉这行,back键不退出activity
        //提示消息刷新
        EventBus.getDefault().post(new LoginEventBean(LoginEventBean.REFRESH_NOTICE));
    }

    /**
     * 使键盘能够弹起
     */
    private void initEditHight() {
        //监听软键盘弹出，并获取软键盘高度
        SoftKeyBoardListener.setListener(this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
                        LinearLayout.LayoutParams layoutParams =
                                (LinearLayout.LayoutParams) view_keybord.getLayoutParams();
                        layoutParams.height = height;
                        view_keybord.setLayoutParams(layoutParams);
                        //键盘一弹起，就将列表滚动到最下面
                        rvIm.scrollToPosition(mImAdapter.getData().size()-1);
                    }

                    @Override
                    public void keyBoardHide(int height) {
                        LinearLayout.LayoutParams layoutParams =
                                (LinearLayout.LayoutParams) view_keybord.getLayoutParams();
                        layoutParams.height = 0;
                        view_keybord.setLayoutParams(layoutParams);
                    }
                });
    }

    @OnClick({R.id.img_im_back, R.id.tv_send,R.id.tv_commodity_state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_im_back:
                //提示消息刷新
                EventBus.getDefault().post(new LoginEventBean(LoginEventBean.REFRESH_NOTICE));
                finish();
                break;
            //发送信息
            case R.id.tv_send:
                //空文字不发送
                if (StringUtil.isEmpty(etIm.getText().toString())) {
                    return;
                }
                sendMsg(etIm.getText().toString());
                break;
            //查看物流/立即购买等
            case R.id.tv_commodity_state:
                startActivity(new Intent(mContext, SureBuyActivity.class));
                break;
        }
    }
}
