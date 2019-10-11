package com.jjz.energy.ui.mine.information;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu.MineAccountBean;
import com.jjz.energy.presenter.mine.MineAccountsPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.mine.IMineAccountsView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 收款账户
 * @author: create by chenhao on 2019/7/25
 */
public class MineAccountsActivity extends BaseActivity<MineAccountsPresenter> implements IMineAccountsView {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_alipay)
    ImageView imgAlipay;
    @BindView(R.id.tv_alipay_name)
    TextView tvAlipayName;
    @BindView(R.id.tv_alipay_phone)
    TextView tvAlipayPhone;
    @BindView(R.id.tv_alipay_update)
    TextView tvAlipayUpdate;
    @BindView(R.id.rb_alipay)
    RadioButton rbAlipay;
    @BindView(R.id.rl_alipay)
    RelativeLayout rlAlipay;
    @BindView(R.id.img_wechat)
    ImageView imgWechat;
    @BindView(R.id.tv_wechat_name)
    TextView tvWechatName;
    @BindView(R.id.tv_wechat_phone)
    TextView tvWechatPhone;
    @BindView(R.id.tv_wechat_update)
    TextView tvWechatUpdate;
    @BindView(R.id.rb_wechat)
    RadioButton rbWechat;
    @BindView(R.id.rl_wechat)
    RelativeLayout rlWechat;
    @BindView(R.id.tv_add_account)
    TextView tvAddAccount;

    /**
     * 是否已添加支付宝
     */
    private boolean isAddAlipay = false;

    /**
     * 是否已添加微信支付
     */
    private boolean isAddWechat = false;

    /**
     * 保存数据
     */
    private MineAccountBean mMineAccountBean;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("收款账户");
        //单选框
        initRb();
    }

    /**
     * 初始化单选框
     */
    private void initRb() {
        //支付宝
        rbAlipay.setOnClickListener(v -> {
            rbAlipay.setText("默认收款账户");
            rbWechat.setText("设为默认");
            rbWechat.setChecked(false);
            setDefaultAccount("alipay");
        });
        //微信
        rbWechat.setOnClickListener(v -> {
            rbWechat.setText("默认收款账户");
            rbAlipay.setText("设为默认");
            rbAlipay.setChecked(false);
            setDefaultAccount("wechat");
        });
    }

    /**
     * 设置默认收款账户
     * @param type wechat  or  alipay
     */
    private void setDefaultAccount(String type ){
        //保存的数据还在
        if (mMineAccountBean!=null){
            HashMap<String ,String> hashMap= new HashMap<>();
            hashMap.put("payee",type);
            if ("alipay".equals(type)){
                hashMap.put("alipay_name",mMineAccountBean.getAlipay().getAlipay_name());
                hashMap.put("alipay_account",mMineAccountBean.getAlipay().getAlipay_account());
                hashMap.put("type","alipay");
            }
            if ("wechat".equals(type)){
                hashMap.put("wechat_name",mMineAccountBean.getWechat().getWechat_name());
                hashMap.put("wechat_account",mMineAccountBean.getWechat().getWechat_account());
                hashMap.put("type","wechat");
            }

            mPresenter.setDefaultAccount(PacketUtil.getRequestPacket(hashMap));
        }


    }

    /**
     * 展示添加账户弹窗
     */
    private void showAddAccount() {
        View popView = View.inflate(mContext, R.layout.pop_add_accounts, null);
        //选中支付宝
        LinearLayout item_ll_alipay = popView.findViewById(R.id.item_ll_alipay);

        //选中微信
        LinearLayout item_ll_wechat = popView.findViewById(R.id.item_ll_wechat);

        //取消
        TextView item_tv_cancle = popView.findViewById(R.id.item_tv_cancle);

        PopupWindow popupWindow = PopWindowUtil.getInstance().showBottomWindow(mContext, popView);

        item_tv_cancle.setOnClickListener(v -> popupWindow.dismiss());

        item_ll_alipay.setOnClickListener(v -> {
            //先判断是否已经存在账户
            if (isAddAlipay){
                showToast("当前已绑定支付宝账户");
            }else {
                popupWindow.dismiss();
                startActivity(new Intent(mContext, BindAliPayActivity.class));
            }
        });
        item_ll_wechat.setOnClickListener(v -> {
            //先判断是否已经存在账户
            if (isAddWechat){
                showToast("当前已绑定微信账户");
            }else {
                popupWindow.dismiss();
                startActivity(new Intent(mContext, BindWechatActivity.class));
            }
        });
    }


    @Override
    public void isGetInfoSuccess(MineAccountBean data) {
        mMineAccountBean = data;
        //默认是微信
        if ("wechat".equals(mMineAccountBean.getPayee())){
            rbWechat.setText("默认收款账户");
            rbAlipay.setText("设为默认");
            rbWechat.setChecked(true);
            rbAlipay.setChecked(false);
        }else{
            //默认支付宝
            rbAlipay.setText("默认收款账户");
            rbWechat.setText("设为默认");
            rbWechat.setChecked(false);
            rbAlipay.setChecked(true);
        }
        //写入支付宝信息
        if (!StringUtil.isEmpty(mMineAccountBean.getAlipay().getAlipay_account())){
            isAddAlipay = true;
            //显示支付宝信息
            rlAlipay.setVisibility(View.VISIBLE);
            tvAlipayName.setText(mMineAccountBean.getAlipay().getAlipay_name());
            tvAlipayPhone.setText(mMineAccountBean.getAlipay().getAlipay_account());
        }else{
            isAddAlipay= false;
        }
        //写入微信信息
        if (!StringUtil.isEmpty(mMineAccountBean.getWechat().getWechat_account())){
            isAddWechat = true;
            //显示微信信息
            rlWechat.setVisibility(View.VISIBLE);
            tvWechatName.setText(mMineAccountBean.getWechat().getWechat_name());
            tvWechatPhone.setText(mMineAccountBean.getWechat().getWechat_account());
        }else{
            isAddWechat= false;
        }
        //如果两个账户都已添加，那么隐藏按钮
        if (isAddAlipay && isAddWechat) {
            tvAddAccount.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_add_account ,R.id.tv_wechat_update,R.id.tv_alipay_update })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //添加
            case R.id.tv_add_account:
                showAddAccount();
                break;
            //微信编辑
            case R.id.tv_wechat_update:
                startActivity(new Intent(mContext,BindWechatActivity.class).putExtra("is_bind_wechat",1));
                break;
            //支付宝编辑
            case R.id.tv_alipay_update:
                startActivity(new Intent(mContext,BindAliPayActivity.class).putExtra("is_bind_alipay",1));
                break;


        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //获取收款账户信息
        mPresenter.getMineAccounts(PacketUtil.getRequestPacket(null));
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
    protected MineAccountsPresenter getPresenter() {
        return new MineAccountsPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_accounts;
    }



    @Override
    public void isPutAccountSuccess(String data) {
        LogUtil.e("收款账户","设置默认收款账户成功");
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }
}
