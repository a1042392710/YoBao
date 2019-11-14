package com.jjz.energy.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.SearchGoodsAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.LikeGoodsBean;
import com.jjz.energy.presenter.MainPresenter;
import com.jjz.energy.ui.home.commodity.CommodityDetailActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.flowlayout.FlowLayout;
import com.jjz.energy.util.flowlayout.TagAdapter;
import com.jjz.energy.util.flowlayout.TagFlowLayout;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.IMainView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 搜索结果
 * @author: create by chenhao on 2019/8/15
 */
public class SearchResultActivity extends BaseActivity<MainPresenter> implements IMainView {

    @BindView(R.id.img_finish)
    ImageView imgFinish;
    @BindView(R.id.dl_group)
    DrawerLayout dlGroup;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rb_sort_comprehensive)
    RadioButton rbSortComprehensive;
    @BindView(R.id.rb_sort_credit)
    RadioButton rbSortCredit;
    @BindView(R.id.rb_sort_region)
    RadioButton rbSortRegion;
    @BindView(R.id.rb_sort_list)
    RadioButton rbSortList;
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
    @BindView(R.id.tv_submit_filter)
    TextView tvSubmitFilter;

    private String[] mVals = {"同城", "包邮", "全新"};
    private String[] mTimeVals = {"24小时", "7天", "30天"};

    /**
     * 记录选中的值 是否全新 ， 包邮 ，同城  ,发布时间
     */
    private String is_mnh = "",is_free_shipping= "",is_my_city= "",put_time= "";
    /**
     * 页码
     */
    private int mPage=1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    /*
     * 搜索类型   1 综合排序（默认）   2 好评优先
     */
    private int search_type = 1;

    private SearchGoodsAdapter mAdapter;

    @Override
    protected void initView() {
        String search_data = getIntent().getStringExtra("search_data");
        etSearch.setText(search_data);
        etSearch.setSelection(etSearch.getText().toString().length());
        //关闭侧滑手势改为手动弹出
        dlGroup.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //初始化商品列表
        initRv();
        //初始化流布局
        initTf();
        // 绑定监听
        initListener();
        //第一次查询
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put("keyword", etSearch.getText().toString());
        mPresenter.searchGoodsResult(PacketUtil.getRequestPacket(map), false);
    }



    /**
     * 初始化侧滑容器中的流布局
     */
    private void initTf() {
        //快捷筛选
        tfQuickScreening.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_filter_flowlayout,
                        tfQuickScreening, false);
                tv.setText(s);
                return tv;
            }
        });

        //快捷筛选 选中事件
        tfQuickScreening.setOnSelectListener(selectPosSet -> {
            is_my_city = "";
            is_free_shipping = "";
            is_mnh = "";
            if (selectPosSet.contains(0))
               is_my_city = "1";      //同城
            if (selectPosSet.contains(1))
                is_free_shipping = "1";      //包邮
            if (selectPosSet.contains(2))
                is_mnh = "1";     //全新
        });

        //发布时间
        tfReleaseTime.setAdapter(new TagAdapter<String>(mTimeVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_filter_flowlayout,
                        tfReleaseTime, false);
                tv.setText(s);
                return tv;
            }
        });
        //时间选中事件
        tfReleaseTime.setOnSelectListener(selectPosSet -> {
            put_time="";
            if (selectPosSet.contains(0))
                put_time = "1";
            if (selectPosSet.contains(1))
                put_time = "7";
            if (selectPosSet.contains(2))
                put_time = "30";
        });
    }

    /**
     * 初始化商品分类列表
     */
    private void initRv() {
        rvSearchResult.setLayoutManager(new GridLayoutManager(mContext, 2));
        mAdapter = new SearchGoodsAdapter(R.layout.item_commodity_grid,
                new ArrayList<>());
        rvSearchResult.setAdapter(mAdapter);
    }


    /**
     * 绑定监听
     */
    private void initListener() {
        //设置侧滑
        dlGroup.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                //打开了侧滑
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                //关闭了侧滑
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        //打开筛选
        rbSortList.setOnClickListener(v -> {
            dlGroup.openDrawer(Gravity.RIGHT);
        });

        //管理筛选条件
        rgSort.setOnCheckedChangeListener((group, checkedId) -> {
            search_type = 0;
            //让图标都变灰色
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_filter_unchecked);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            rbSortList.setCompoundDrawables(null, null, drawable, null);
            Drawable drawable2 = mContext.getResources().getDrawable(R.mipmap.ic_down_unchecked);
            drawable2.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            rbSortRegion.setCompoundDrawables(null, null, drawable2, null);
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
                //筛选条件
                case R.id.rb_sort_list:
                    Drawable checkedDrawable = mContext.getResources().getDrawable(R.mipmap.ic_filter_checked);
                    checkedDrawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    rbSortList.setCompoundDrawables(null, null, checkedDrawable, null);
                    break;
                //区域排序
                case R.id.rb_sort_region:
                    Drawable checkedDrawable2 = mContext.getResources().getDrawable(R.mipmap.ic_down_checked);
                    checkedDrawable2.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    rbSortRegion.setCompoundDrawables(null, null, checkedDrawable2, null);
                    break;
            }
        });

        //跳转商品详情
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                startActivity(new Intent(mContext, CommodityDetailActivity.class)
                        .putExtra(Constant.GOODS_ID, mAdapter.getItem(position).getGoods_id())));
        //上拉加载
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getData(true);
        });
    }

    /**
     * 重置筛选
     */
    private void resetSearch(){
        etHighestPrice.setText("");
        etLowestPrice.setText("");
        //快捷筛选
        tfQuickScreening.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_filter_flowlayout,
                        tfQuickScreening, false);
                tv.setText(s);
                return tv;
            }
        });
        //发布时间
        tfReleaseTime.setAdapter(new TagAdapter<String>(mTimeVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_filter_flowlayout,
                        tfReleaseTime, false);
                tv.setText(s);
                return tv;
            }
        });
    }


    /**
     * 开始查询
     */
    private void getData( boolean isLoadMore ){
        this.isLoadMore = isLoadMore;
        HashMap<String,String>map = new HashMap<>();
        map.put("page",mPage+"");
        map.put("keyword",etSearch.getText().toString());
        if (!StringUtil.isEmpty(is_mnh)){
            map.put("is_mnh","1");//是否全新
        }
        if (!StringUtil.isEmpty(is_free_shipping)){
            map.put("is_free_shipping","1");  //是否包邮
        }
        if (!StringUtil.isEmpty(put_time)){
            map.put("day",put_time);//发布时间
        }
        //最低价
        if (!StringUtil.isEmpty(etLowestPrice.getText().toString())){
            map.put("low_price",etLowestPrice.getText().toString());
        }
        //最高价
        if (!StringUtil.isEmpty(etHighestPrice.getText().toString())){
            map.put("top_price",etHighestPrice.getText().toString());
        }
        //搜索 todo 排序条件
        if (search_type!=0){
            map.put("sort_type",search_type+"");
        }
        //是否同城
        if (!StringUtil.isEmpty(is_my_city)){
            //根据逗号分隔到List数组中  上传用户当前定位
            String address = SpUtil.init(mContext).readString(Constant.LOCATION_ADDRESS);
            List<String> list= Arrays.asList(address.split("/"));
            if (list.size()>2){
                //地区
                map.put("province",list.get(0));
                map.put("city",list.get(1));
                map.put("district",list.get(2));
            }
        }
        mPresenter.searchGoodsResult(PacketUtil.getRequestPacket(map),isLoadMore);
    }

    @Override
    public void isGetSearchGoodsResultSuccess(LikeGoodsBean data) {
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data, "没有更多搜索结果", false,
                        null));
                smartRefresh.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                if (data.getList().size()>6){
                    smartRefresh.setEnableLoadMore(true);
                }else{
                    smartRefresh.setEnableLoadMore(false);
                }
            }
        }
        closeRefresh(smartRefresh);
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
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @OnClick({R.id.img_finish, R.id.tv_submit_filter ,R.id.tv_reset_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_finish:
                finish();
                break;
                //提交筛选
            case R.id.tv_submit_filter:
                disMissSoftKeyboard();
                //关闭侧滑
                dlGroup.closeDrawer(Gravity.RIGHT);
                getData(false);
                break;
                //重置
            case R.id.tv_reset_filter:
                resetSearch();
                break;
        }
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_search_result;
    }


}
