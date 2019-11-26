package com.jjz.energy.ui.jiusu_shop;

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
import com.jjz.energy.base.Constant;
import com.jjz.energy.ui.home.SearchResultActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.flowlayout.FlowLayout;
import com.jjz.energy.util.flowlayout.TagAdapter;
import com.jjz.energy.util.flowlayout.TagFlowLayout;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.system.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 搜索商铺
 * @author: create by chenhao on 2019/6/20
 */
public class SearchShopActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tl_search)
    TagFlowLayout tlSearch;
    @BindView(R.id.img_search_clear)
    ImageView imgSearchClear;


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
        etSearch.setHint("搜索热门店铺");
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
     * 给TagFlowLayout 赋值
     */
    private void initFlowLayout() {
        //从本地获取历史记录
        String historyStr = SpUtil.init(mContext).readString(Constant.SEARCH_HISTORY_SHOP);
        if (StringUtil.isEmpty(historyStr)) {
            return;
        }
        String[] mVals = historyStr.split(",");
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
            startActivity(new Intent(mContext, SearchShopResultActivity.class).putExtra("search_data"
                    , mVals[position]));
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


    @OnClick({R.id.img_back, R.id.tv_search, R.id.img_search_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            //搜索
            case R.id.tv_search:
                String searchStr = etSearch.getText().toString();
                if (StringUtil.isEmpty(searchStr)) {
                    return;
                }
                //将本次搜索记录存到本地
                String history =
                        SpUtil.init(mContext).readString(Constant.SEARCH_HISTORY_SHOP) + (searchStr + ",");
                SpUtil.init(mContext).commit(Constant.SEARCH_HISTORY_SHOP, history);
                startActivity(new Intent(mContext, SearchResultActivity.class).putExtra(
                        "search_data", searchStr));
                finish();
                break;
            //清除历史记录
            case R.id.img_search_clear:
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您是否要清除全部历史搜索记录", () -> {
                    SpUtil.init(mContext).commit(Constant.SEARCH_HISTORY_SHOP, "");
                    tlSearch.setVisibility(View.GONE);
                });
                break;
            default:
                break;
        }
    }
}
