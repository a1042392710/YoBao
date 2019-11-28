package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jjz.energy.R;
import com.jjz.energy.adapter.jiusu.SellerOrderAdapter;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;
import com.jjz.energy.presenter.jiusu.MineSellerOrderPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineSellerOrderView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @Features: 卖家的订单 列表
 * @author: create by chenhao on 2019/4/15
 */
public class JiuSuMineSellerOrderFragment extends BaseLazyFragment<MineSellerOrderPresenter> implements IMineSellerOrderView {
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
    private SellerOrderAdapter mOrderAdapter;

    @Override
    protected MineSellerOrderPresenter getPresenter() {
        return new MineSellerOrderPresenter(this);
    }

    @Override
    protected void initView() {
        mStatus = getArguments().getString("status");
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        mOrderAdapter = new SellerOrderAdapter(R.layout.item_mine_seller_order,new ArrayList<>(),this);
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
            startActivity(new Intent(mContext, JiuSuMineSellerOrderDetailActvity.class)
                    .putExtra("order_sn",((JiuSuOrderBean.ListBean)adapter.getData().get(position)).getOrder_sn()));
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
        mPresenter.getSellerOrderList(PacketUtil.getRequestPacket(map), isShowLoading);
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
     * 确认订单
     */
    public void confirmOrder(String order_sn) {
        mPresenter.confirmOrder(PacketUtil.getRequestPacket(Utils.stringToMap("order_sn",order_sn)));
    }
    @Override
    public void isConfirmOrderSuccess(String data) {
        mOrderAdapter.notifyPosition();
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


    //=================== 多用 ===================

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

}
