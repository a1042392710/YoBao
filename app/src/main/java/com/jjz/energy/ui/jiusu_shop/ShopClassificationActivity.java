package com.jjz.energy.ui.jiusu_shop;

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
import com.jjz.energy.entry.jiusu_shop.JiuSuShopClassBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 商家分类
 * @author: create by chenhao on 2019/10/7
 */
public class ShopClassificationActivity extends BaseActivity <JiuSuShopPresenter>implements IJiuSuShopView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_classification)
    RecyclerView rvClassification;

    private MyClassificationAdapter mShopTypeAdapter;

    @Override
    protected JiuSuShopPresenter getPresenter() {
        return new JiuSuShopPresenter(this);
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
        mPresenter.getShopClass(PacketUtil.getRequestPacket(null));
    }

    /**
     * 初始化列表数据
     */
    private void initRv() {
        rvClassification.setLayoutManager(new LinearLayoutManager(this));
        mShopTypeAdapter = new MyClassificationAdapter(R.layout.item_classification, new ArrayList<>());
        rvClassification.setAdapter(mShopTypeAdapter);

        mShopTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            JiuSuShopClassBean.ClassListBean  classListBean =  mShopTypeAdapter.getItem(position);
            startActivity(new Intent(mContext,ShopCateListActivity.class).putExtra("title",classListBean.getMobile_name()).putExtra("cate_id",classListBean.getId()));
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
    public void isGetShopClassSuccess(JiuSuShopClassBean data) {
        //绑定分类信息
        mShopTypeAdapter.notifyChangeData(data.getList());
    }


    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    /**
     * 分类列表适配器
     */
    class MyClassificationAdapter extends BaseRecycleNewAdapter<JiuSuShopClassBean.ClassListBean>{

        public MyClassificationAdapter(int layoutResId, @Nullable List<JiuSuShopClassBean.ClassListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, JiuSuShopClassBean.ClassListBean item) {
            //分类名称
            helper.setText(R.id.item_tv_classification_title, item.getMobile_name());
        }
    }
}
