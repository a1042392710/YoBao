package com.jjz.energy.ui.jiusu_shop;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.JiuSuShopListAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.flowlayout.TagFlowLayout;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 搜索商铺结果
 * @author: create by chenhao on 2019/8/15
 */
public class SearchShopResultActivity extends BaseActivity<JiuSuShopPresenter> implements IJiuSuShopView {


    @BindView(R.id.img_finish)
    ImageView imgFinish;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rb_sort_comprehensive)
    RadioButton rbSortComprehensive;
    @BindView(R.id.rb_sort_credit)
    RadioButton rbSortCredit;
    @BindView(R.id.cb_on_city)
    CheckBox cbOnCity;
    @BindView(R.id.rg_sort)
    RadioGroup rgSort;
    @BindView(R.id.rv_search_result)
    RecyclerView rvSearchResult;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tf_quick_screening)
    TagFlowLayout tfQuickScreening;
    @BindView(R.id.et_lowest_price)
    EditText etLowestPrice;
    @BindView(R.id.et_highest_price)
    EditText etHighestPrice;
    @BindView(R.id.tf_release_time)
    TagFlowLayout tfReleaseTime;
    @BindView(R.id.tv_reset_filter)
    TextView tvResetFilter;
    @BindView(R.id.tv_submit_filter)
    TextView tvSubmitFilter;
    @BindView(R.id.dl_group)
    DrawerLayout dlGroup;
    /**
     * 页码
     */
    private int mPage = 1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;
    /**
     * 是否同城
     */
    private String is_my_city= "";

    /*
     * 搜索类型   1 综合排序（默认）   2 好评优先
     */
    private int search_type = 1;

    private JiuSuShopListAdapter mAdapter;

    @Override
    protected void initView() {
        String search_data = getIntent().getStringExtra("search_data");
        etSearch.setText(search_data);
        etSearch.setSelection(etSearch.getText().toString().length());
        //关闭侧滑手势改为手动弹出
        dlGroup.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //初始化商品列表
        initRv();
        // 绑定监听
        initListener();
        //第一次查询
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put("keyword", etSearch.getText().toString());
        mPresenter.getSearchShopList(PacketUtil.getRequestPacket(map), false);
    }


    /**
     * 初始化商品分类列表
     */
    private void initRv() {
        //商户列表  显示一些推荐商户
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new JiuSuShopListAdapter(R.layout.item_jiusu_shop_list, new ArrayList<>());
        rvSearchResult.setAdapter(mAdapter);
    }


    /**
     * 绑定监听
     */
    private void initListener() {
        //是否同城
        cbOnCity.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                is_my_city = "";
            }else{
                is_my_city = "1";
            }
            mPage= 1 ;
            getData(false);
        });
        //管理筛选条件
        rgSort.setOnCheckedChangeListener((group, checkedId) -> {
            mPage=1;
            search_type = 0;
            //选中后图标变红
            switch (checkedId) {
                //综合排序
                case R.id.rb_sort_comprehensive:
                    search_type = 1;
                    getData(false);
                    break;
                //信用排序
                case R.id.rb_sort_credit:
                    search_type = 2;
                    getData(false);
                    break;
            }
        });

        // 跳转商家个人主页
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                startActivity(new Intent(mContext,JiuSuShopHomePageActivity.class).putExtra("id",mAdapter.getItem(position).getId())));
        //上拉加载
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getData(true);
        });
    }


    /**
     * 开始查询
     */
    private void getData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put("keyword", etSearch.getText().toString());
        //搜索
        if (search_type != 0) {
            map.put("sort_type", search_type + "");
        }
        //是否同城
        if (!StringUtil.isEmpty(is_my_city)) {
            //根据逗号分隔到List数组中  上传用户当前定位
            String address = SpUtil.init(mContext).readString(Constant.LOCATION_ADDRESS);
            List<String> list = Arrays.asList(address.split("/"));
            if (list.size() > 2) {
                //地区
                map.put("province", list.get(0));
                map.put("city", list.get(1));
                map.put("district", list.get(2));
            }
        }
        mPresenter.getSearchShopList(PacketUtil.getRequestPacket(map), isLoadMore);
    }
    @Override
    public void isGetClassAndShopListSuccess(JiuSuShopBean data) {
        if (!StringUtil.isListEmpty(data.getList()) && data.getList().size()>8){
            smartRefresh.setEnableLoadMore(true);     //有够长的数据就开启加载更多
        }else{
            smartRefresh.setEnableLoadMore(false);     //否则关闭
        }
        //加载更多
        if (isLoadMore) {
            mAdapter.addNewData(data.getList());
        } else {
            //刷新数据，无数据显示空页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "没有更多搜索结果", true, v -> {
                    getData(false);
                }));
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg ,boolean isNetError) {
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @OnClick({R.id.img_finish, R.id.tv_submit_filter, R.id.tv_reset_filter, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_finish:
                finish();
                break;
            //搜索
            case R.id.tv_search:
                mPage = 1;
                getData(false);
                break;


        }
    }

    @Override
    protected JiuSuShopPresenter getPresenter() {
        return new JiuSuShopPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_search_shop_result;
    }



}
