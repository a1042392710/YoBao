package com.jjz.energy.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.jjz.energy.adapter.HomeGoodsAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.ui.home.commodity.CommodityDetailActivity;
import com.jjz.energy.util.flowlayout.FlowLayout;
import com.jjz.energy.util.flowlayout.TagAdapter;
import com.jjz.energy.util.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 搜索结果
 * @author: create by chenhao on 2019/8/15
 */
public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.img_finish)
    ImageView imgFinish;
    @BindView(R.id.dl_group)
    DrawerLayout dlGroup;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_notice)
    ImageView imgNotice;
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
    SwipeRefreshLayout smartRefresh;
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
     * 快捷筛选的选中下标
     */
    private int quickScreeningIndex  =0;
    /**
     * 发布时间的选中下标
     */
    private int timeIndex  =0;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_search_result;
    }

    @Override
    protected void initView() {
        String search_data = getIntent().getStringExtra("search_data");
        etSearch.setText(search_data);
        etSearch.setSelection(etSearch.getText().toString().length());
        //关闭侧滑手势改为手动弹出
        dlGroup.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        // 初始化商品列表
        initRv();
        //初始化流布局
        initTf();
        // 绑定监听
        initListener();
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
        //点击事件
        tfQuickScreening.setOnTagClickListener((view, position, parent) -> {
            //记录选中的下标
            quickScreeningIndex = position;
            return true;
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
        //点击事件
        tfReleaseTime.setOnTagClickListener((view, position, parent) -> {
            //记录选中的下标
            timeIndex = position;
            return true;
        });
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
                    break;
                //信用排序
                case R.id.rb_sort_credit:
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
                    checkedDrawable2.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    rbSortRegion.setCompoundDrawables(null, null, checkedDrawable2, null);
                    break;
            }
        });
    }

    /**
     * 初始化商品分类列表
     */
    private void initRv() {
        rvSearchResult.setLayoutManager(new GridLayoutManager(mContext, 2));

        HomeGoodsAdapter commodityTypeAdapter =
                new HomeGoodsAdapter(R.layout.item_commodity_grid, new ArrayList<>());
        rvSearchResult.setAdapter(commodityTypeAdapter);

        commodityTypeAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(mContext, CommodityDetailActivity.class)));
    }


    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @OnClick({R.id.img_finish, R.id.img_notice, R.id.tv_submit_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_finish:
                finish();
                break;
            case R.id.img_notice:
                //通知
                break;

                //提交筛选
            case R.id.tv_submit_filter:
                //关闭侧滑
                dlGroup.closeDrawer(Gravity.RIGHT);
                break;
        }
    }



}
