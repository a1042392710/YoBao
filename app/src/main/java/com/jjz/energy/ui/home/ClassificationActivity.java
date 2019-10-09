package com.jjz.energy.ui.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 分类
 * @author: create by chenhao on 2019/10/7
 */
public class ClassificationActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_classification)
    RecyclerView rvClassification;

    private MyClassificationAdapter mAdapter;


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_classification;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("分类");
        rvClassification.setLayoutManager(new LinearLayoutManager(this));
        List<String>list = new ArrayList<>();
        list.add("手机");
        list.add("数码");
        list.add("服装");
        list.add("手机");
        list.add("手机");
        list.add("手机");
        list.add("手机");
        list.add("手机");
        mAdapter = new MyClassificationAdapter(R.layout.item_classification, list);
        rvClassification.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent();
            intent.putExtra("classification_title", mAdapter.getData().get(position));
            setResult(RESULT_OK, intent);
            finish();
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

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    /**
     * 分类列表适配器
     */
    class MyClassificationAdapter extends BaseRecycleNewAdapter<String>{

        public MyClassificationAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_tv_classification_title, item);
        }
    }
}
