package com.jjz.energy.ui.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjz.energy.R;
import com.jjz.energy.adapter.HomeCommodityTypeAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 搜索结果
 * @author: create by chenhao on 2019/8/15
 */
public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.img_finish)
    ImageView imgFinish;
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
        // 初始化商品列表
        initRv();
        // 绑定监听
        initListener();
    }
    /**
     * 绑定监听
     */
    private void initListener() {
        rgSort.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                //综合排序
                case R.id.rb_sort_comprehensive:
                    break;
                //信用排序
                case R.id.rb_sort_credit:
                    break;
                //筛选条件  弹出侧滑
                case R.id.rb_sort_list:
                    break;
                //区域排序
                case R.id.rb_sort_region:
                    break;
            }
        });
    }

    /**
     * 初始化商品分类列表
     */
    private void initRv() {
        rvSearchResult.setLayoutManager(new GridLayoutManager(mContext, 2));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        HomeCommodityTypeAdapter commodityTypeAdapter =
                new HomeCommodityTypeAdapter(R.layout.item_commodity_grid, list);
        rvSearchResult.setAdapter(commodityTypeAdapter);

        commodityTypeAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(mContext,CommodityDetailActivity.class)));
    }


    @Override
    public void showLoading() {
    startProgressDialog();
    }

    @Override
    public void stopLoading() {
    stopProgressDialog();
    }

    @OnClick({R.id.img_finish, R.id.img_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_finish:
                finish();
                break;
            case R.id.img_notice:
                //通知
                break;
        }
    }
}
