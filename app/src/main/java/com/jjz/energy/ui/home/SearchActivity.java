package com.jjz.energy.ui.home;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
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
    @BindView(R.id.card_search)
    CardView cardSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tl_search)
    TagFlowLayout tlSearch;

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
        //给TagFlowLayout 赋值
        initFlowLayout();
    }

    /**
     * 给TagFlowLayout 赋值
     */
    private void initFlowLayout() {
        String[] mVals = {"清洁剂","尾气清洁剂","久速","清洁剂","尾气","久速","清洁剂","陈大帅","周星星","清洁剂","尾气清洁剂","久速" };
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
