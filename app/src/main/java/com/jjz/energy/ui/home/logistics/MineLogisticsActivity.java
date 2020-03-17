package com.jjz.energy.ui.home.logistics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.MineLogisticsAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.home.LogisticsListBean;
import com.jjz.energy.presenter.home.logistics.LogisticsPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.home.ILogisticsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 物流中心
 * @author: create by chenhao on 2019/6/28
 */
public class MineLogisticsActivity extends BaseActivity<LogisticsPresenter> implements ILogisticsView, OnRefreshLoadMoreListener {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    /**
     * 页码
     */
    private int mPage = 1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;
    /**
     * 列表适配器
     */
    private MineLogisticsAdapter mAdapter;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我的物流");
        smartRefresh.setOnRefreshLoadMoreListener(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MineLogisticsAdapter(R.layout.item_mine_logistics, new ArrayList<>(), this);
        rvGoods.setAdapter(mAdapter);
        //进入详情
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext, LogisticsDetailActivity.class).putExtra("id",
                    mAdapter.getData().get(position).getId()));
        });
        getData(false);
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore) {
        if (!isLoadMore) {
            mPage = 1;
        }
        this.isLoadMore = isLoadMore;
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put("my_issue", "1");
        mPresenter.getLogisticsInfo(PacketUtil.getRequestPacket(map), isLoadMore);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        getData(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = 1;
        getData(false);
    }

    /**
     * 撤销的条目下标
     */
    private int cancleIndex ;
    /**
     * 删除发布
     *
     * @param id
     */
    public void cancleLogistics(String id,int position) {
        cancleIndex = position;
        PopWindowUtil.getInstance().showPopupWindow(mContext, "是否撤销该条发布信息？", () -> {
            mPresenter.cancleLogistics(PacketUtil.getRequestPacket(Utils.stringToMap("id", id)));
        });
    }

    @Override
    public void isCancleLogisticsSuc(String data) {
        showToast("撤销成功");
        mAdapter.remove(cancleIndex);
    }

    @Override
    public void isGetLogisticsInfoSuc(LogisticsListBean data) {
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
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "暂无数据", true,
                        v -> {
                    getData(true);
                }));
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isNetAndServiceError) {
            //网络错误时刷新页码
            mPage = 1;
            mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_timeout, "网络发生错误", true, v -> {
                getData(false);
            }));
        }
        //请求失败时页码归位
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(smartRefresh);
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
    protected LogisticsPresenter getPresenter() {
        return new LogisticsPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_logistics;
    }



    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}
