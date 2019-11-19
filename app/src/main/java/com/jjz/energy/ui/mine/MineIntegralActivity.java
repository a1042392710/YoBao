package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.jjz.energy.base.BaseWebActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.MineIntegralBean;
import com.jjz.energy.presenter.mine.MineIntegralPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IMineIntegralView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我的积分
 * @author: create by chenhao on 2019/11/16
 */
public class MineIntegralActivity extends BaseActivity<MineIntegralPresenter> implements IMineIntegralView, OnLoadMoreListener {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    /**
     * 页码
     */
    private int mPage = 1;
    /**
     * 是否属于上拉加载
     */
    private boolean isLoadMore = false;

    private MineIntegralAdapter mAdapter;


    @Override
    protected void initView() {
        tvToolbarTitle.setText("我的积分");
        smartRefresh.setOnLoadMoreListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MineIntegralAdapter(R.layout.item_integral_list, new ArrayList<>());
        rvList.setAdapter(mAdapter);
        getData(false);
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore) {
        Map<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        mPresenter.getIntegralList(PacketUtil.getRequestPacket(map), isLoadMore);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        isLoadMore = true;
        getData(false);
    }

    @Override
    public void isGetIntegralSuccess(MineIntegralBean data) {
        //显示可用积分
        tvIntegral.setText(""+data.getPoints());
        if (!StringUtil.isListEmpty(data.getList()) && data.getList().size() > 8) {
            smartRefresh.setEnableLoadMore(true);     //有够长的数据就开启加载更多
        } else {
            smartRefresh.setEnableLoadMore(false);     //否则关闭
        }
        //加载更多
        if (isLoadMore) {
            mAdapter.addNewData(data.getList());
        } else {
            //刷新数据，无数据显示空页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data,"暂无记录",false,null));
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg) {
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_toolbar_right:
                //积分规则
                startActivity(new Intent(mContext, BaseWebActivity.class)
                        .putExtra(BaseWebActivity.WEB_TITLE ,"积分规则").putExtra(BaseWebActivity.WEB_URL,  Constant.INTEGRAL_RULE));
                break;
        }
    }


    class MineIntegralAdapter extends BaseRecycleNewAdapter<MineIntegralBean.ListBean> {

        public MineIntegralAdapter(int layoutResId, @Nullable List<MineIntegralBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineIntegralBean.ListBean item) {
            helper.setText(R.id.item_tv_title,item.getDesc());
            helper.setText(R.id.item_tv_time, DateUtil.longToDate(item.getChange_time(),null));
            if (item.getPay_points().contains("-")){
                helper.setText(R.id.item_tv_num,""+item.getPay_points());
                helper.setTextColor(R.id.item_tv_num,getResources().getColor(R.color.red_fe8977));
            }else{
                helper.setText(R.id.item_tv_num,"+"+item.getPay_points());
                helper.setTextColor(R.id.item_tv_num,getResources().getColor(R.color.text_green_26a69a));
            }
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
    protected MineIntegralPresenter getPresenter() {
        return new MineIntegralPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_integral;
    }
}
