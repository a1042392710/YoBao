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
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.ui.ImagePagerActivity;
import com.jjz.energy.ui.mine.shop_order.SureBuyActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.SoftKeyBoardListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
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
     * 指定下标
     */
    private int position;
    /**
     * 要和谁聊天
     */
    private String userName;

    private com.jjz.energy.entry.UserInfo mUserInfo;

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
        //用户消息
        mUserInfo = UserLoginBiz.getInstance(mContext).readUserInfo();
        //获取列表中的指定数据，然后展开会话
        position = getIntent().getIntExtra("position", 0);
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
        mImAdapter = new ImAdapter(R.layout.item_im, new ArrayList<>(),this);
        mImAdapter.setUserName(mUserInfo.getMobile());
        rvIm.setAdapter(mImAdapter);
        rvIm.setOnTouchListener((v, event) -> {
            //触摸列表的时候，缩回软键盘
            disMissSoftKeyboard();
            return false;
        });
        //开启全局监听事件
        JMessageClient.registerEventReceiver(this);
        //进入会话状态,不接收通知栏
        JMessageClient.enterSingleConversation(mUserInfo.getMobile());
    }


    //初始化聊天数据
    public void initImData() {
        //如果会话存在，则更新本地会话
        if (conversation != null) {
            conversation = JMessageClient.getSingleConversation(userName);
        } else {
            //会话不存在，则获取会话列表中的指定会话
            List<Conversation> msgList = JMessageClient.getConversationList();
            //指定position的会话不为空
            if (!StringUtil.isListEmpty(msgList) && msgList.get(position) != null) {
                conversation = msgList.get(position);
            }
        }
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
        //如果是图片，可以查看大图
        mImAdapter.setOnItemClickListener((adapter, v, position) -> {
            //添加图片
            List<String> photo = new ArrayList<>();
            photo.add(((ImageContent)mImAdapter.getData().get(position).getContent()).getLocalThumbnailPath());
            //查看大图
            ImagePagerActivity.ImageSize imageSize =
                    new ImagePagerActivity.ImageSize(v.getMeasuredWidth(),
                    v.getMeasuredHeight());
            ImagePagerActivity.startImagePagerActivity(BaseApplication.AppContext, photo,
                    0, imageSize);
        });

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
        //解绑聊天事件
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
