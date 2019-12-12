package com.jjz.energy.ui.home.jiusu;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.jiusu.AgencyBean;
import com.jjz.energy.presenter.jiusu.MineAgencyPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineAgencyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我的代理
 * @author: create by chenhao on 2019/4/3
 */
public class JiuSuMineAgencyActivity extends BaseActivity<MineAgencyPresenter> implements IMineAgencyView {
    /**
     * 代理类型
     */
    public static final String AGENCY_TYPE = "agency_type";
    //非会员
    public static final int TYPE_NO_VIP = 1;
    //体验会员
    public static final int TYPE_MEMBER = 2;
    //经销商
    public static final int TYPE_DEALER = 3;
    //加盟商
    public static final int TYPE_ALLIANCE_BUSINESS = 4;
    //服务商
    public static final int TYPE_SERVICE_PROVIDER = 5;
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tl_layout)
    TabLayout tlLayout;
    @BindView(R.id.view_liner)
    View viewLiner;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
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
     * 选中的代理类型
     */
    private int mSelectType ;

    private MyAdapter mMyAdapter;

    @Override
    protected MineAgencyPresenter getPresenter() {
        return new MineAgencyPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_agency;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我的代理");
        initTabelLayout();
        initRecycle();
        initListener();
        getData();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabelLayout() {
        //传递过来的代理类型
        mSelectType = getIntent().getIntExtra(AGENCY_TYPE, TYPE_MEMBER);
        tlLayout.addTab(tlLayout.newTab().setText("普通会员").setTag(TYPE_NO_VIP));
        tlLayout.addTab(tlLayout.newTab().setText("VIP1").setTag(TYPE_MEMBER));
        tlLayout.addTab(tlLayout.newTab().setText("VIP2").setTag(TYPE_DEALER));
        tlLayout.addTab(tlLayout.newTab().setText("VIP3").setTag(TYPE_ALLIANCE_BUSINESS));
        tlLayout.addTab(tlLayout.newTab().setText("代理服务商").setTag(TYPE_SERVICE_PROVIDER));
        //设置选中 由于type 是1为起点，下标0为起点，所以设置选中要-1
        tlLayout.getTabAt(mSelectType-1).select();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycle() {
        //初始化Recyclerview
        rvRecord.setLayoutManager(new LinearLayoutManager(this));
        mMyAdapter = new MyAdapter(R.layout.item_mine_agency,new ArrayList<>());
        rvRecord.setAdapter(mMyAdapter);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //选中事件
        tlLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mSelectType = (int) tab.getTag();
                //启用下拉刷新
                smartRefresh.setEnableLoadMore(true);
                //刷新列表
                getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //禁止刷新
        smartRefresh.setEnableRefresh(false);
        //刷新
        smartRefresh.setOnLoadMoreListener(refreshLayout -> loadMore());
    }

    /**
     * 获取数据
     */
    private void getData(){
        mPage = 1;
        Map<String, String> map = new HashMap<>();
        map.put("page", mPage+"");
        map.put("level_id", mSelectType+"");
        mPresenter.getAgency(PacketUtil.getRequestPacket(map),true);
    }
    /**
     * 加载更多
     */
    private void loadMore(){
        mPage++;
        isLoadMore=true;
        Map<String, String> map = new HashMap<>();
        map.put("page", mPage+"");
        map.put("level_id", mSelectType+"");
        mPresenter.getAgency(PacketUtil.getRequestPacket(map),false);
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
    public void isSuccess(AgencyBean data) {
        if (isLoadMore) {
            //加载更多
            isLoadMore = false;
           if (!mMyAdapter.addNewData(data.getList())){
               smartRefresh.setEnableLoadMore(false);
           }
        } else {
            mMyAdapter.notifyChangeData(data.getList());
        }
        //关闭刷新
        closeRefresh(smartRefresh);
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

    class MyAdapter extends BaseRecycleNewAdapter<AgencyBean.ListBean> {

        public MyAdapter(int layoutResId, @Nullable List<AgencyBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AgencyBean.ListBean item) {
            //电话，姓名，注册时间
            helper.setText(R.id.item_tv_phone, item.getMobile());
            helper.setText(R.id.item_tv_name, item.getNickname());
            helper.setText(R.id.item_tv_time, DateUtil.longToDate(item.getReg_time(),null));
        }
    }


    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}
