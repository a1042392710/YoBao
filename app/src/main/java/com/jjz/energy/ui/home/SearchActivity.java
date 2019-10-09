package com.jjz.energy.ui.home;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.util.flowlayout.FlowLayout;
import com.jjz.energy.util.flowlayout.TagAdapter;
import com.jjz.energy.util.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 搜索
 * @author: create by chenhao on 2019/6/20
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tl_search)
    TagFlowLayout tlSearch;
    //搜索类型
    public static final String SEARCH_TYPE = "search_type";
    //搜索商铺
    public static final int SEARCH_SHOP = 1;
    //搜索商品
    public static final int SEARCH_COMMODITY = 2;
    //默认为搜索商品
    private  int searchType  ;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_search;
    }

    @Override
    protected void initView() {
        //搜索类型
        searchType = getIntent().getIntExtra(SEARCH_TYPE , SEARCH_COMMODITY);
        initSearch();
        //给TagFlowLayout 赋值
        initFlowLayout();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initEnterTransition() {
        super.initEnterTransition();
        //设置activity 切换动画效果 淡入淡出 5.0以上才可实现
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade().setDuration(150));
        getWindow().setExitTransition(new Fade().setDuration(150));
    }

    /**
     * 初始化搜索页面
     */
    private void initSearch() {
        switch (searchType){
            case SEARCH_COMMODITY:
                etSearch.setHint("搜索热门宝贝");
                break;

            case SEARCH_SHOP:
                etSearch.setHint("搜索热门店铺");
                break;
        }


    }

    private String[] mVals = {"清洁剂", "尾气清洁剂", "久速", "清洁剂", "尾气", "久速", "清洁剂", "陈大帅", "周星星", "清洁剂"
            , "尾气清洁剂", "久速"};

    /**
     * 给TagFlowLayout 赋值
     */
    private void initFlowLayout() {

        final LayoutInflater mInflater = LayoutInflater.from(this);
        tlSearch.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_search,
                        tlSearch, false);
                tv.setText(s);
                return tv;
            }
        });
        //点击事件
        tlSearch.setOnTagClickListener((view, position, parent) -> {
            //记录选中的意见下标
//            seleteFeedPosition = String.valueOf(position + 1);
            startActivity(new Intent(mContext,SearchResultActivity.class).putExtra("search_data",mVals[position]));
            finish();
            return true;
        });
    }

    @Override
    public void showLoading() {
    startProgressDialog();
    }

    @Override
    public void stopLoading() {
    stopProgressDialog();
    }



    @OnClick({R.id.img_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
                //搜索
            case R.id.tv_search:
                break;
        }
    }
}
