package com.jjz.energy.ui.home.pension;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 养老专区
 * @author: create by chenhao on 2019/7/9
 */
public class PensionActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.rv_pension_type)
    RecyclerView rvPensionType;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rv_pension)
    RecyclerView rvPension;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_pension;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("养老专区");
        GlideUtils.loadImage(mContext,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2704359486,1169599406&fm=26&gp=0.jpg",imgBanner);
        initRv();
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        List<String> list = new ArrayList<>();
        list.add("养老机构");
        list.add("健康就医");
        list.add("健康养老");
        list.add("在线预约");
        rvPensionType.setLayoutManager(new GridLayoutManager(this,4));
        //禁用列表的滑动，使其跟随ScrollView
        rvPension.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //初始化适配器
        rvPension.setAdapter( new PensionAdapter(R.layout.item_pension,list));
        PensionTypeAdapter PensionTypeAdapter =  new PensionTypeAdapter(R.layout.item_pension_type,list);
        rvPensionType.setAdapter(PensionTypeAdapter);
        PensionTypeAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
    }

    //养老分类
    class PensionTypeAdapter extends BaseRecycleNewAdapter<String> {

        public PensionTypeAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_tv_insurance_type,item);
        }
    }

    //生活推荐
    class PensionAdapter extends BaseRecycleNewAdapter<String>{

        public PensionAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView img = helper.getView(R.id.item_img_photo);
            GlideUtils.loadRoundCircleImage(mContext,"http://img0.imgtn.bdimg.com/it/u=3315130648,1748189790&fm=26&gp=0.jpg",img);
        }
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
}
