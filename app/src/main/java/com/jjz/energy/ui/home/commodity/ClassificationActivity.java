package com.jjz.energy.ui.home.commodity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.GoodsClassificationBean;
import com.jjz.energy.presenter.home.PutCommodityPresenter;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.home.IPutCommodityView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 分类
 * @author: create by chenhao on 2019/10/7
 */
public class ClassificationActivity extends BaseActivity <PutCommodityPresenter>implements IPutCommodityView {

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
    protected PutCommodityPresenter getPresenter() {
        return new PutCommodityPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_classification;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("分类");
        initRv();
        //获取分类数据
        mPresenter.getClassification(PacketUtil.getRequestPacket(null));
    }

    /**
     * 初始化列表数据
     */
    private void initRv() {
        rvClassification.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyClassificationAdapter(R.layout.item_classification, new ArrayList<>());
        rvClassification.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_KEY_OBJECT, (Serializable) mAdapter.getData().get(position));
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

    @Override
    public void isGetClassificationSuc(List<GoodsClassificationBean> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    /**
     * 分类列表适配器
     */
    class MyClassificationAdapter extends BaseRecycleNewAdapter<GoodsClassificationBean>{

        public MyClassificationAdapter(int layoutResId, @Nullable List<GoodsClassificationBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsClassificationBean item) {
            //分类名称
            helper.setText(R.id.item_tv_classification_title, item.getMobile_name());
        }
    }
}
