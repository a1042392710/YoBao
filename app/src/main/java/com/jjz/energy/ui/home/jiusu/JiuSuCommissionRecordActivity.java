package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
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
import com.jjz.energy.entry.jiusu.MineWalletListBean;
import com.jjz.energy.presenter.jiusu.MineWalletPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineWalletView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 佣金记录
 * @author: create by chenhao on 2019/4/3
 */
public class JiuSuCommissionRecordActivity extends BaseActivity<MineWalletPresenter> implements IMineWalletView {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private MyAdapter mMyAdapter;
    @Override
    protected MineWalletPresenter getPresenter() {
        return new MineWalletPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_commission_record;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("佣金记录");
        initSetting();
        getData(true);
    }

    /**
     * 初始化配置
     */
    private void initSetting() {
        smartRefresh.setEnableLoadMore(false);
        smartRefresh.setOnRefreshListener(refreshLayout -> {
            getData(false);
        });
        rvRecord.setLayoutManager(new LinearLayoutManager(this));
        mMyAdapter = new MyAdapter(R.layout.item_commission_record,new ArrayList<>());
        rvRecord.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext, JiuSuCommissionDetailActivity.class)
                    .putExtra("title",mMyAdapter.getData().get(position).getDate()));
        });

    }

    /**
     * 刷新数据
     */
    private void getData(boolean isShowLoading){
        mPresenter.getBalanceList(PacketUtil.getRequestPacket(null),isShowLoading);
    }

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
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
    public void isBalanceListSuccess(List<MineWalletListBean> data) {
        if (StringUtil.isListEmpty(data)){
            View view = View.inflate(mContext, R.layout.item_empty, null);
            mMyAdapter.setEmptyView(view);
            smartRefresh.setEnableRefresh(false);
            return;
        }
        mMyAdapter.notifyChangeData(data);
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
        closeRefresh(smartRefresh);
    }


    class MyAdapter  extends BaseRecycleNewAdapter<MineWalletListBean> {

        public MyAdapter(int layoutResId, @Nullable List<MineWalletListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineWalletListBean item) {
            //标题
            helper.setText(R.id.item_tv_title,item.getDate()+"佣金记录");
            helper.setText(R.id.item_tv_label_content_one,item.getNet_money());
            helper.setText(R.id.item_tv_label_content_two,item.getSpec_money());
            helper.setText(R.id.item_tv_label_content_three,item.getPush_money());
            helper.setText(R.id.item_tv_label_content_four,item.getTotal_money());
        }
    }
}
