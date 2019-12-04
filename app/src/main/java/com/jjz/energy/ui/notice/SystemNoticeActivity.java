package com.jjz.energy.ui.notice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.OrderNoticeBean;
import com.jjz.energy.presenter.home.NoticePresenter;
import com.jjz.energy.ui.home.commodity.CommodityDetailActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.home.INoticeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 系统消息
 * @author: create by chenhao on 2019/11/9
 */
public class SystemNoticeActivity extends BaseActivity<NoticePresenter> implements INoticeView,OnLoadMoreListener {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_notice_list)
    RecyclerView rvNoticeList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private NoticeAdapter mNoticeAdapter;

    /**
     * 页码
     */
    private int mPage = 1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("系统消息");
        smartRefresh.setOnLoadMoreListener(this);
        rvNoticeList.setLayoutManager(new LinearLayoutManager(this));
        mNoticeAdapter = new NoticeAdapter(R.layout.item_order_notice, new ArrayList<>());
        rvNoticeList.setAdapter(mNoticeAdapter);
        //点击进入各个详情页面
        mNoticeAdapter.setOnItemClickListener((adapter, view, position) -> {
            //进入商品详情页面
            startActivity(new Intent(mContext, CommodityDetailActivity.class).putExtra(Constant.GOODS_ID, mNoticeAdapter.getData().get(position).getGoods_id()));
        });
        getData(false);
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        HashMap<String,String> map = new HashMap<>();
        map.put("enum","system");
        map.put("page",mPage+"");
        mPresenter.getOrderNoticeList(PacketUtil.getRequestPacket(map),isLoadMore);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        getData(true);
    }



    @Override
    public void isGetOrderNoticeSuc(OrderNoticeBean data) {
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mNoticeAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mNoticeAdapter.notifyChangeData(data.getList())) {
                mNoticeAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "您还没有系统消息", false,
                        null));
                smartRefresh.setEnableLoadMore(false);
            } else {
                if (data.getList().size()>6){
                    //有数据就开启加载更多
                    smartRefresh.setEnableLoadMore(true);
                }else{
                    //有数据就开启加载更多
                    smartRefresh.setEnableLoadMore(false);
                }
            }
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

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void isFail(String msg) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    @Override
    protected NoticePresenter getPresenter() {
        return new NoticePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_notice_list;
    }

    class NoticeAdapter extends BaseRecycleNewAdapter<OrderNoticeBean.ListBean>{

        public NoticeAdapter(int layoutResId, @Nullable List<OrderNoticeBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderNoticeBean.ListBean item) {
            ImageView imgCommodity = helper.getView(R.id.item_img_commondity);
            GlideUtils.loadRoundCircleImage(mContext,item.getImg_url(),imgCommodity);
            helper.setText(R.id.item_tv_notice_title,item.getTitle());
            helper.setText(R.id.item_tv_notice_content,item.getContent());
            helper.setText(R.id.item_tv_time_title, DateUtil.longToDate(item.getAdd_time(),null));
        }
    }
}
