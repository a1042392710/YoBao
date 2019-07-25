package com.jjz.energy.ui.mine;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.entry.BindBean;
import com.jjz.energy.presenter.BindALiAndWechatPresenter;
import com.jjz.energy.util.SpUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.IBindALiAndWechatView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 绑定微信
 * @author: create by chenhao on 2019/4/11
 */
public class BindWechatActivity extends BaseActivity<BindALiAndWechatPresenter> implements IBindALiAndWechatView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_wechat_nickname)
    TextView tvWechatNickname;
    @BindView(R.id.tv_is_bind_wechat)
    TextView tvIsBindWechat;
    @BindView(R.id.tv_bind_toast)
    TextView tvBindToast;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_bind_warm_prompt)
    TextView tvBindWarmPrompt;
    /**
     * 是否绑定微信
     */
    private int isBindWechat ;

    private IWXAPI mApi;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("管理微信钱包");
        EventBus.getDefault().register(this);
        isBindWechat = getIntent().getIntExtra("is_bind_wechat",0);
        String is_wechat_login = getIntent().getStringExtra("is_wechat_login");
        //是否授权
        if (!StringUtil.isEmpty(is_wechat_login)){
            tvIsBindWechat.setText("已授权");
            tvWechatNickname.setText(SpUtil.init(mContext).readString("wechat_nickname"));
            tvBindToast.setVisibility(View.GONE);
        }
        //已经绑定微信
        if(isBindWechat==1){
            tvBind.setText("安全修改");
            //查询绑定的微信信息
            mPresenter.getBindInfo(PacketUtil.getRequestPacket(Utils.stringToMap("type","wechat")));
        }
    }

    /**
     * 微信登录
     */
    private void wechatLogin() {
        startProgressDialog();
        mApi = WXAPIFactory.createWXAPI(getApplicationContext(), Constant.WX_APP_ID, true);
        LogUtils.e("WXEntryActivity.APP_ID", "" + Constant.WX_APP_ID);
        mApi.registerApp(Constant.WX_APP_ID);

        if (mApi != null && mApi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test_neng";
            mApi.sendReq(req);
//            finish();
        } else {
            showToast("用户未安装微信");
        }
        stopLoading();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBindInfo(LoginEventBean event) {
        if (event.getLoginStatus() == LoginEventBean.WECHAT_LOG_IN) {
            //存储授权信息
            SpUtil.init(mContext).commit("wechat_nickname", event.getValue()+"");
            tvIsBindWechat.setText("已授权");
            //写入微信昵称
            tvWechatNickname.setText(event.getValue());
            //隐藏绑定提示
            tvBindToast.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void isPutSuccess(String data) {
        showToast("操作成功");
        EventBus.getDefault().post(new LoginEventBean(LoginEventBean.WECHAT_BIND_SUC));
        tvBind.setText("安全修改");
    }

    @Override
    public void isGetSuccess(BindBean data) {
        //写入微信数据
        etName.setText(data.getWechat_name());
        etPhone.setText(data.getWechat_account());
        tvWechatNickname.setText(data.getNickname());
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_is_bind_wechat, R.id.tv_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //跳转微信授权
            case R.id.tv_is_bind_wechat:
                wechatLogin();
                break;
            //提交授权信息
            case R.id.tv_bind:
                if (StringUtil.isEmpty(etName.getText().toString())) {
                    showToast("请输入真实姓名");
                    return;
                }
                if (StringUtil.isEmpty(etPhone.getText().toString())){
                    showToast("请输入手机号码");
                    return;
                }
                if ("未授权".equals(tvIsBindWechat.getText().toString())){
                    showToast("请微信授权");
                    return;
                }
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("wechat_name",etName.getText().toString());
                hashMap.put("wechat_account",etPhone.getText().toString());
                hashMap.put("type","wechat");
                mPresenter.putBindInfo(PacketUtil.getRequestPacket(hashMap));
                break;
        }
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
    protected BindALiAndWechatPresenter getPresenter() {
        return new BindALiAndWechatPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_bind_wechat;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }
}
