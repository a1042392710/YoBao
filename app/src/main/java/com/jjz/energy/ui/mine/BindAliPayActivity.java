package com.jjz.energy.ui.mine;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.LoginEventBean;
import com.jjz.energy.entry.BindBean;
import com.jjz.energy.presenter.BindALiAndWechatPresenter;
import com.jjz.energy.util.SpUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.IBindALiAndWechatView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 绑定支付宝
 * @author: create by chenhao on 2019/4/11
 */
public class BindAliPayActivity extends BaseActivity<BindALiAndWechatPresenter> implements IBindALiAndWechatView {


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
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_bind_warm_prompt)
    TextView tvBindWarmPrompt;
    /**
     * 是否绑定支付宝
     */
    private int isBindAlipay ;

    @Override
    protected BindALiAndWechatPresenter getPresenter() {
        return new BindALiAndWechatPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_bind_alipay;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("管理支付宝钱包");
        isBindAlipay = getIntent().getIntExtra("is_bind_alipay",0);
        //已经绑定支付宝
        if(isBindAlipay==1){
            tvBind.setText("安全修改");
            //查询绑定的支付宝信息
            mPresenter.getBindInfo(PacketUtil.getRequestPacket(Utils.stringToMap("type","alipay")));
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


    @OnClick({R.id.ll_toolbar_left, R.id.tv_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //提交支付宝信息
            case R.id.tv_bind:
                String account = etPhone.getText().toString();
                if (StringUtil.isEmpty(etName.getText().toString())){
                    showToast("请输入真实姓名");
                return;
                }
                if (StringUtil.isEmpty(account)){
                    showToast("请输入支付宝账号");
                    return;
                }
                if (StringUtil.isMobile(account)|| StringUtil.isEmail(account)){
                    HashMap <String,String> hashMap = new HashMap<>();
                    hashMap.put("type","alipay");
                    hashMap.put("alipay_name",etName.getText().toString().trim());
                    hashMap.put("alipay_account",etPhone.getText().toString().trim());
                    mPresenter.putBindInfo(PacketUtil.getRequestPacket(hashMap));
                }else{
                    showToast("请输入正确的支付宝账号");
                }
                break;
        }
    }

    @Override
    public void isPutSuccess(String data) {
        SpUtil.init(mContext).commit("is_bind_alipay","1");
        showToast("操作成功");
        EventBus.getDefault().post(new LoginEventBean(LoginEventBean.ALIPAY_BIND_SUC));
        tvBind.setText("安全修改");
    }

    @Override
    public void isGetSuccess(BindBean data) {
        //写入支付宝数据
        etName.setText(data.getAlipay_name());
        etPhone.setText(data.getAlipay_account());
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }
}
