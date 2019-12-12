package com.jjz.energy.ui.home.jiusu;

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
import com.jjz.energy.entry.jiusu.YoCardReceiveListBean;
import com.jjz.energy.presenter.jiusu.JiuSuMineVipPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IJiuSuMineVipView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features:  赠油领取记录
 * @author: create by chenhao on 2019/4/20
 */
public class JiuSuYoCardReceiveListActivity extends BaseActivity<JiuSuMineVipPresenter> implements IJiuSuMineVipView {

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
    private GetListAdapter mGetAdapter;

    /**
     * 页码
     */
    private int mPage = 1;
    /**
     * 是否属于上拉加载
     */
    private boolean isLoadMore = false;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("获取记录");
        mGetAdapter = new GetListAdapter(R.layout.item_yocard_receive_list, new ArrayList<>());
        rvRecord.setLayoutManager(new LinearLayoutManager(mContext));
        rvRecord.setAdapter(mGetAdapter);
        //刷新
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
        getData(true);

    }

    /**
     * 获取数据
     */
    private void getData(boolean isShowLoading) {
        Map<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        mPresenter.getYoGiftList(PacketUtil.getRequestPacket(map), isShowLoading);
    }

    @Override
    public void isListSuccess(YoCardReceiveListBean data) {
        //加载更多
        if (isLoadMore) {
            isLoadMore = false;
            if (!mGetAdapter.addNewData(data.getList())){
                smartRefresh.setEnableLoadMore(false);
            }
        } else {
            if (StringUtil.isListEmpty(data.getList())){
                View view = View.inflate(mContext, R.layout.item_empty, null);
                mGetAdapter.setEmptyView(view);
                smartRefresh.setEnableRefresh(false);
                return;
            }
            mGetAdapter.notifyChangeData(data.getList());
        }
        //关闭刷新
        closeRefresh(smartRefresh);
    }



    //获取记录
    class GetListAdapter extends BaseRecycleNewAdapter<YoCardReceiveListBean.ListBean> {

        public GetListAdapter(int layoutResId, @Nullable List<YoCardReceiveListBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, YoCardReceiveListBean.ListBean item) {
            helper.setText(R.id.item_tv_title,
                    item.getDesc());
            helper.setText(R.id.item_tv_time, DateUtil.longToDate(item.getChange_time(),null));
            //设置颜色
            if (0 < item.getOil_balance()) {
                helper.setText(R.id.itme_tv_num, "+"+item.getOil_balance()+"L");
                helper.setTextColor(R.id.itme_tv_num,
                        getResources().getColor(R.color.text_green_26a69a));
            } else if (0 > item.getOil_balance()) {
                helper.setText(R.id.itme_tv_num, +item.getOil_balance()+"L");
                helper.setTextColor(R.id.itme_tv_num,
                        getResources().getColor(R.color.red_fe8977));
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

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
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
    protected JiuSuMineVipPresenter getPresenter() {
        return new JiuSuMineVipPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_yocard_receive_list;
    }

}
