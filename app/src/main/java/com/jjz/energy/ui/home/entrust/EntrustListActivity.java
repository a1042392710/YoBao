package com.jjz.energy.ui.home.entrust;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.EntrustListAdpter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.entry.home.EntrustListBean;
import com.jjz.energy.presenter.home.EntrustListPresenter;
import com.jjz.energy.ui.mine.information.MineAccountsActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.view.home.IEntrustListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 委托大厅
 * @author: create by chenhao on 2019/11/25
 */
public class EntrustListActivity extends BaseActivity<EntrustListPresenter> implements IEntrustListView, OnRefreshLoadMoreListener {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_shopping_list)
    RecyclerView rvShoppingList;
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

    private EntrustListAdpter mAdapter;

    private UserInfo mUserInfo;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("委托大厅");
        tvToolbarRight.setText("发布");
        mUserInfo = UserLoginBiz.getInstance(mContext).readUserInfo();
        //绑定刷新和加载
        smartRefresh.setOnRefreshLoadMoreListener(this);
        mAdapter = new EntrustListAdpter(R.layout.item_home_entrust, new ArrayList<>(),this);
        rvShoppingList.setLayoutManager(new LinearLayoutManager(mContext));
        rvShoppingList.setAdapter(mAdapter);
        getData(false);
    }

    /**
     * 获取数据 0为主页列表 1为发布者，2为接收者
     *
     * @param isLoadMore 是否加载更多
     */
    private void getData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String,String> map  = new HashMap<>();
        map.put("page",mPage+"");
        map.put("mine","0");
        mPresenter.getEntrustList(PacketUtil.getRequestPacket(map), isLoadMore);
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

    @Override
    public void isGetEntrustListSuc(EntrustListBean data) {
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
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "还没有人发布委托",
                        true, v -> {
                    getData(false);
                }));
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    /**
     * 选中的条目
     */
    private int selectPosition ;

    /**
     * 接受委托
     *
     * @param order_sn
     */
    public void accpetEntrust(String order_sn, int position) {
        if (1==mUserInfo.getIs_bind_wechat() || 1 ==mUserInfo.getIs_bind_alipay()) {
            selectPosition = position;
            mPresenter.accpetEnturst(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,
                    order_sn)));
        }else{
            showToast("请先添加收款账户");
            startActivity(new Intent(mContext, MineAccountsActivity.class));
        }
    }


    @Override
    public void isAccpetEntrustSuc(String msg) {
        showToast("您已接受此委托，请尽快完成");
        mAdapter.remove(selectPosition);
        startActivity(new Intent(mContext,MineEntrustActivity.class));
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
    protected int layoutId() {
        return R.layout.act_jiusu_shopping_list;
    }

    @Override
    protected EntrustListPresenter getPresenter() {
        return new EntrustListPresenter(this);
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_toolbar_right:
                //发布委托
                startActivity(new Intent(mContext, PutEntrustActivity.class));
                break;
        }
    }
}
