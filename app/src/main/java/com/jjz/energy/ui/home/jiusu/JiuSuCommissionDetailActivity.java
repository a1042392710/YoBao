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
import com.jjz.energy.entry.jiusu.CommissionDetailBean;
import com.jjz.energy.presenter.jiusu.MineWalletPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineWalletView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features:  佣金月详细记录
 * @author: create by chenhao on 2019/4/20
 */
public class JiuSuCommissionDetailActivity extends BaseActivity<MineWalletPresenter> implements IMineWalletView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;

    private GetListAdapter mGetAdapter;
    /**
     * 日期
     */
    private String date;

    @Override
    protected void initView() {
        date = getIntent().getStringExtra("title");
        tvToolbarTitle.setText(date+"佣金记录");
        mGetAdapter = new GetListAdapter(R.layout.item_commission_list, new ArrayList<>());
        rvRecord.setLayoutManager(new LinearLayoutManager(mContext));
        rvRecord.setAdapter(mGetAdapter);
        mPresenter.getCommissionDetail(PacketUtil.getRequestPacket(Utils.stringToMap("date",date)));
    }

    @Override
    public void isGetCommissionDeatil(CommissionDetailBean data) {
        if (StringUtil.isListEmpty(data.getList())){
            View view = View.inflate(mContext, R.layout.item_empty, null);
            mGetAdapter.setEmptyView(view);
            return;
        }
        mGetAdapter.notifyChangeData(data.getList());
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
        return R.layout.act_commission_detail_list;
    }


    //佣金详情记录
    class GetListAdapter extends BaseRecycleNewAdapter<CommissionDetailBean.ListBean> {

        public GetListAdapter(int layoutResId, @Nullable List<CommissionDetailBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommissionDetailBean.ListBean item) {
            //来自谁的佣金
            helper.setText(R.id.item_tv_title,"来自"+item.getNickname()+"的"+item.getType());
            //时间
            helper.setText(R.id.item_tv_time, DateUtil.longToDate(item.getCreate_time(),null));
            //金额
            helper.setText(R.id.itme_tv_price,"+"+item.getCommission_money());
            //状态
            helper.setText(R.id.itme_tv_state,item.getStatus());
        }
    }
}
