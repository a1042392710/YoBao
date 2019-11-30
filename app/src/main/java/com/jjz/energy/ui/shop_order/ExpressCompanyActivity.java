package com.jjz.energy.ui.shop_order;

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
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.presenter.order.ExpressPresenter;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.order.IExpressView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 选择物流公司
 * @author: create by chenhao on 2019/10/7
 */
public class ExpressCompanyActivity extends BaseActivity <ExpressPresenter>implements IExpressView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_classification)
    RecyclerView rvClassification;

    private ExpressCompanyAdapter mAdapter;


    @Override
    protected ExpressPresenter getPresenter() {
        return new ExpressPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_classification;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("选择物流公司");
        initRv();
        //获取物流公司数据
        mPresenter.getExpressCompany(PacketUtil.getRequestPacket(null));
    }

    /**
     * 初始化列表数据
     */
    private void initRv() {
        rvClassification.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExpressCompanyAdapter(R.layout.item_classification, new ArrayList<>());
        rvClassification.setAdapter(mAdapter);
        //点击物流公司后返回上一级页面
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_KEY_OBJECT, (Serializable) mAdapter.getData().get(position));
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void isGetExpressCompanySuc(List<ExpressCompanyBean> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
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


    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    /**
     * 分类列表适配器
     */
    class ExpressCompanyAdapter extends BaseRecycleNewAdapter<ExpressCompanyBean>{

        public ExpressCompanyAdapter(int layoutResId, @Nullable List<ExpressCompanyBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ExpressCompanyBean item) {
            //分类名称
            helper.setText(R.id.item_tv_classification_title, item.getName());
        }
    }
}
