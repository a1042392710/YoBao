package com.jjz.energy.ui.home.jiusu;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.jiusu.WithdrawListBean;
import com.jjz.energy.presenter.jiusu.MineWalletPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineWalletView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features:  提现记录
 * @author: create by chenhao on 2019/4/20
 */
public class JiuSuWithdrawDepositListActivity extends BaseActivity<MineWalletPresenter> implements IMineWalletView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;

    private GetListAdapter mGetAdapter;


    @Override
    protected void initView() {
        tvToolbarTitle.setText("提现记录");
        mGetAdapter = new GetListAdapter(R.layout.item_withdraw_desposit_list, new ArrayList<>());
        rvRecord.setLayoutManager(new LinearLayoutManager(mContext));
        rvRecord.setAdapter(mGetAdapter);
        mPresenter.getWithdrawList(PacketUtil.getRequestPacket(null));
    }

    @Override
    public void isWithdrawListSuccess(List<WithdrawListBean> data) {
        if (StringUtil.isListEmpty(data)){
            View view = View.inflate(mContext, R.layout.item_empty, null);
            mGetAdapter.setEmptyView(view);
            return;
        }
        mGetAdapter.notifyChangeData(data);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }
    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }



    @Override
    public void isFail(String msg) {
        showToast(msg);
    }
    @Override
    protected MineWalletPresenter getPresenter() {
        return new MineWalletPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_withdraw_deposit_list;
    }


    //提取记录
    class GetListAdapter extends BaseRecycleNewAdapter<WithdrawListBean> {

        public GetListAdapter(int layoutResId, @Nullable List<WithdrawListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WithdrawListBean item) {
//            helper.setText(R.id.item_tv_title,
//                    "提现到" + item.getTypeName() + "(" + item.getTotal_money() + "元)，实际到账"
//                            +item.getPay_money()+"元，税金"+item.getTotal_taxes()+"元，手续费"+item.getCharge()+"元");

            helper.setText(R.id.item_tv_title,
                    "提现到" + item.getTypeName() + "(" + item.getTotal_money() + "元)，实际到账"
                            +item.getPay_money()+"元");
            helper.setText(R.id.item_tv_time, DateUtil.longToDate(item.getApply_time(),null));
            if (1 > item.getStatus()) {
                helper.setTextColor(R.id.itme_tv_num,
                        getResources().getColor(R.color.text_green_26a69a));
            } else if (0 > item.getStatus()) {
                helper.setTextColor(R.id.itme_tv_num,
                        getResources().getColor(R.color.text_red_f76060));
            } else {
                helper.setTextColor(R.id.itme_tv_num,
                        getResources().getColor(R.color.text_orange_f7bc2e));
            }
            helper.setText(R.id.itme_tv_num, item.getStatusName());

        }
    }
}
