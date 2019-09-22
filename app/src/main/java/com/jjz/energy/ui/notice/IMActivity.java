package com.jjz.energy.ui.notice;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.jjz.energy.R;
import com.jjz.energy.alipay.ImAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
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

    /**
     * 聊天的适配器
     */
    private ImAdapter mImAdapter;

    /**
     * 会话对象
     */
    private Conversation conversation;

    private int position;
    /**
     * 要和谁聊天
     */
    private String userName = "fxj123";
    /**
     * 是否第一次刷新数据
     */
    private boolean one = true;

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
        position = getIntent().getIntExtra("position", 0);
        initEt();
        initImRv();
        initJpush();
        initData();
    }

    private void initEt() {

    }

    /**
     * 初始化聊天
     */
    private void initJpush() {
        //注册聊天事件监听
        JMessageClient.registerEventReceiver(this);
        //创建会话，如果存在则返回会话实例
        conversation = Conversation.createSingleConversation(userName, null);
        //进入会话状态,不接收通知栏
//        JMessageClient.enterSingleConversation(this.userName);
    }

    /**
     * 初始化列表
     */
    private void initImRv(){
        rvIm.setLayoutManager(new LinearLayoutManager(this));
        //绑定聊天数据
        mImAdapter = new ImAdapter(R.layout.item_im,new ArrayList<>());
        rvIm.setAdapter(mImAdapter);
    }


    //初始化数据
    public void initData() {
        //获取列表中的会话  position 为列表页面传来的
//        List<Conversation> msgList = JMessageClient.getConversationList();
//        if (msgList != null) {
//            if (msgList.size() > 0) {
//                if (msgList.get(position) != null) {
//                    conversation = msgList.get(position);
//                    //重置会话未读消息数
//                    conversation.resetUnreadCount();
//
//                }
//            }
//        }
        //如果会话存在，则显示聊天内容
        if (conversation != null) {
            tvImTitle.setText(conversation.getTitle() == null ? "" : conversation.getTitle());
            UserInfo info = (UserInfo) conversation.getTargetInfo();
            userName = info.getUserName();
            //userName = "f8443445-a7ef-47d8-8005-b0d57851b396";  //todo 可自定义

            //使列表滚动到底部
            if (conversation.getAllMessage() != null) {
                if (conversation.getAllMessage().size() > 0) {
                    //设置数据
                    mImAdapter.setNewData(conversation.getAllMessage());
//                    //设置刷新不闪屏
//                    ((SimpleItemAnimator) rvIm.getItemAnimator()).setSupportsChangeAnimations(false);
//                    if (one) {
//                        mImAdapter.notifyDataSetChanged();
//                    } else {
//                        mImAdapter.notifyItemInserted(conversation.getAllMessage().size() - 1);
//
//                    }
                    rvIm.scrollToPosition(conversation.getAllMessage().size() - 1);
                }
            }
            //如果是图片，可以查看大图
            mImAdapter.setOnItemClickListener((adapter, view, position) -> {

            });
        }
        one=false; // 代表不是第一次initData
    }

    /**
     *  接收在线消息
     **/
    public void onEventMainThread(MessageEvent event) {
        //获取事件发生的会话对象
        Message newMessage = event.getMessage();
        LogUtil.e("在线",String.format(Locale.SIMPLIFIED_CHINESE, "收到一条来自%s的在线消息。\n", conversation.getTargetId()));
        initData();
    }


    /**
     * 接收离线消息。
     * 类似MessageEvent事件的接收，上层在需要的地方增加OfflineMessageEvent事件的接收
     * 即可实现离线消息的接收。
     **/
    public void onEvent(OfflineMessageEvent event) {
        //获取事件发生的会话对象
        Conversation conversation = event.getConversation();
        List<Message> newMessageList = event.getOfflineMessageList();//获取此次离线期间会话收到的新消息列表
        System.out.println(String.format(Locale.SIMPLIFIED_CHINESE, "收到%d条来自%s的离线消息。\n", newMessageList.size(), conversation.getTargetId()));
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

    @OnClick({R.id.img_im_back, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_im_back:
                finish();
                break;
                //发送信息
            case R.id.tv_send:
                Message message =JMessageClient.createSingleTextMessage(userName,null,etIm.getText().toString());
                //发送消息
                JMessageClient.sendMessage(message);
                showLoading();
                message.setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        stopLoading();
                        if (i==0){
                            //刷新数据
                            initData();
                            etIm.setText("");
                        }else{
                            showToast(s);
                        }
                    }
                });

                break;
        }
    }
}
