package com.jjz.energy.ui.mine.information;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.util.PopWindowUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 收款账户
 * @author: create by chenhao on 2019/7/25
 */
public class MineAccountsActivity extends BaseActivity {
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
    @BindView(R.id.tv_alipay_delete)
    TextView tvAlipayDelete;
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
    @BindView(R.id.tv_wechat_delete)
    TextView tvWechatDelete;
    @BindView(R.id.rb_wechat)
    RadioButton rbWechat;
    @BindView(R.id.rl_wechat)
    RelativeLayout rlWechat;
    @BindView(R.id.tv_add_account)
    TextView tvAddAccount;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_accounts;
    }

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
        rbAlipay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                rbAlipay.setText("默认收款账户");
                rbWechat.setChecked(false);
            }else{
                rbAlipay.setText("设为默认");
            }
        });
        rbWechat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                rbWechat.setText("默认收款账户");
                rbAlipay.setChecked(false);
            }else{
                rbWechat.setText("设为默认");
            }
        });

    }

    /**
     * 展示添加账户弹窗
     */
    private void showAddAccount() {
        View popView = View.inflate(mContext, R.layout.pop_add_accounts, null);
        //选中支付宝
        LinearLayout item_ll_alipay = popView.findViewById(R.id.item_ll_alipay);
        item_ll_alipay.setOnClickListener(v -> {
            //todo 先判断是否已经存在账户
            startActivity(new Intent(mContext, BindAliPayActivity.class));
        });
        //选中微信
        LinearLayout item_ll_wechat = popView.findViewById(R.id.item_ll_wechat);
        item_ll_wechat.setOnClickListener(v -> {
            //todo 先判断是否已经存在账户
            startActivity(new Intent(mContext, BindWechatActivity.class));
        });
        //取消
        TextView item_tv_cancle = popView.findViewById(R.id.item_tv_cancle);

        PopupWindow popupWindow = PopWindowUtil.getInstance().showBottomWindow(mContext, popView);

        item_tv_cancle.setOnClickListener(v -> popupWindow.dismiss());


    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_add_account ,R.id.tv_alipay_delete,R.id.tv_wechat_delete,R.id.tv_wechat_update,R.id.tv_alipay_update })
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
                startActivity(new Intent(mContext,BindWechatActivity.class));
                break;
            //微信删除
            case R.id.tv_wechat_delete:
                rlWechat.setVisibility(View.GONE);
                break;
            //支付宝编辑
            case R.id.tv_alipay_update:
                startActivity(new Intent(mContext,BindAliPayActivity.class));
                break;
            //支付宝删除
            case R.id.tv_alipay_delete:
                    rlAlipay.setVisibility(View.GONE);
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


}
