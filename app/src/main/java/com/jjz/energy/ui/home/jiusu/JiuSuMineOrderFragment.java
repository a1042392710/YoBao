package com.jjz.energy.ui.home.jiusu;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jjz.energy.R;
import com.jjz.energy.adapter.jiusu.JiuSuOrderAdapter;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;
import com.jjz.energy.presenter.jiusu.MineOrderPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineOrderView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @Features: 我的订单 列表
 * @author: create by chenhao on 2019/4/15
 */
public class JiuSuMineOrderFragment extends BaseLazyFragment<MineOrderPresenter> implements IMineOrderView {
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    /**
     * 页码
     */
    private int mPage = 1;
    /**
     * 是否属于上拉加载
     */
    private boolean isLoadMore = false;
    /**
     * 订单状态
     */
    private String mStatus;
    /**
     * 订单列表适配器
     */
    private JiuSuOrderAdapter mOrderAdapter;

    @Override
    protected MineOrderPresenter getPresenter() {
        return new MineOrderPresenter(this);
    }

    @Override
    protected void initView() {
        mStatus = getArguments().getString("status");
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        mOrderAdapter = new JiuSuOrderAdapter(R.layout.item_mine_order,new ArrayList<>());
        mOrderAdapter.openLoadAnimation();
        rvOrder.setAdapter(mOrderAdapter);
        initListener();
        getData(true);
    }

    /**
     * 监听
     */
    private void initListener() {
        mOrderAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivityForResult(new Intent(mContext, JiuSuMineOrderDetailActvity.class).putExtra("order_sn",((JiuSuOrderBean.ListBean)adapter.getData().get(position)).getOrder_sn()),10);
        });

        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                isLoadMore=true;
                getData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage=1;
                smartRefresh.setEnableLoadMore(true);
                getData(false);
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData(boolean isShowLoading) {
        Map<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put("is_over", mStatus);
        mPresenter.getOrderList(PacketUtil.getRequestPacket(map), isShowLoading);
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_mine_order;
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
    public void isSuccess(JiuSuOrderBean data) {
        //加载更多
        if (isLoadMore) {
            isLoadMore = false;
            if (!mOrderAdapter.addNewData(data.getList())){
                smartRefresh.setEnableLoadMore(false);
            }
        } else {
            mOrderAdapter.notifyChangeData(data.getList());
            if (StringUtil.isListEmpty(data.getList())){
                View view = View.inflate(mContext, R.layout.item_empty,null);
                mOrderAdapter.setEmptyView(view);
                setEnableSmart(false);
            }else {
                setEnableSmart(true);
            }
        }
        //关闭刷新
        closeRefresh(smartRefresh);
    }

    /**
     * 启用禁用 刷新和加载
     * @param isEnable
     */
    private void setEnableSmart(boolean isEnable){
        smartRefresh.setEnableLoadMore(isEnable);
        smartRefresh.setEnableRefresh(isEnable);
    }

    @Override
    public void isFail(String msg) {
        //关闭刷新
        if (isLoadMore) {
            isLoadMore = false;
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK) {
            mPage = 1;
            getData(true);
        }
    }
}
