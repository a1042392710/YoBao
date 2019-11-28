package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu.MineWalletBean;
import com.jjz.energy.presenter.jiusu.MineWalletPresenter;
import com.jjz.energy.ui.mine.information.OwnerInfoActivity;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.jiusu.IMineWalletView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我的佣金
 * @author: create by chenhao on 2019/4/1
 */
public class JiuSuMineWalletActivity extends BaseActivity<MineWalletPresenter> implements IMineWalletView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_money_symbol)
    TextView tvMoneySymbol;
    @BindView(R.id.tv_withdraw_deposit)
    TextView tvWithdrawDeposit;
    @BindView(R.id.tv_banlance)
    TextView tvBanlance;
    @BindView(R.id.tv_banlance_text)
    TextView tvBanlanceText;
    @BindView(R.id.view_moeny_line)
    View viewMoenyLine;

    @Override
    protected MineWalletPresenter getPresenter() {
        return new MineWalletPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_wallet;
    }
    //等级Id
    private int level_id;
    @Override
    protected void initView() {
        tvToolbarTitle.setText("我的佣金");
        tvToolbarRight.setText("佣金记录");
        level_id = getIntent().getIntExtra("level_id",2);
        tvBanlance.setClickable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getBalance(PacketUtil.getRequestPacket(null));
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right, R.id.tv_withdraw_deposit, R.id.tv_banlance_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //提现
            case R.id.tv_withdraw_deposit:
                //是否进行实名认证
                int is_set_idcard= SpUtil.init(mContext).readInt(Constant.IS_SET_IDCARD);
                if (is_set_idcard==0) {
                    PopWindowUtil.getInstance().showPopupWindow(mContext, "请先进行实名认证方可提现", () -> {
                        startActivity(new Intent(mContext, OwnerInfoActivity.class));
                    });
                }else {
                    //传输可提现余额到提现页面
                    startActivity(new Intent(mContext, JiuSuWithdrawDepositActivity.class).putExtra(
                            "apply_commissions", mWalletBean.getApply_commissions())
                            .putExtra("total_commissions", mWalletBean.getTotal_commissions()));
                }
                break;
            //佣金记录
            case R.id.tv_toolbar_right:
                startActivity(new Intent(mContext, JiuSuCommissionRecordActivity.class));
                break;
            //佣金余额文字
            case R.id.tv_banlance_text:
//                startActivity(new Intent(mContext,ExpireDateActivity.class));
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

    /**
     * 佣金信息
     */
    private MineWalletBean mWalletBean = new MineWalletBean();

    @Override
    public void isBalanceSuccess(MineWalletBean data) {
        mWalletBean = data;
        if (data.getTotal_commissions().equals("0")) {
            //佣金余额
            tvBanlance.setText(data.getTotal_commissions() + ".00");
        } else {
            //佣金余额
            tvBanlance.setText(data.getTotal_commissions());
        }
//        //中级会员及以上会员可提现佣金
//        if (level_id<=2){
//            tvBanlanceText.setText("佣金未激活");
//            tvBanlanceText.setClickable(true);
//            viewMoenyLine.setVisibility(View.VISIBLE);
//            tvBanlanceText.setTextColor(getResources().getColor(R.color.text_red_f76060));
//        }
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }


}
