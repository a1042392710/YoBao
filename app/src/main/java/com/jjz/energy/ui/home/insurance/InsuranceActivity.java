package com.jjz.energy.ui.home.insurance;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
 * @Features: 保险专区
 * @author: create by chenhao on 2019/7/8
 */
public class InsuranceActivity extends BaseActivity {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.tv_activity_title_one)
    TextView tvActivityTitleOne;
    @BindView(R.id.tv_activity_content_one)
    TextView tvActivityContentOne;
    @BindView(R.id.tv_activity_big_one)
    TextView tvActivityBigOne;
    @BindView(R.id.tv_activity_big_unit_one)
    TextView tvActivityBigUnitOne;
    @BindView(R.id.ll_activity_one)
    LinearLayout llActivityOne;
    @BindView(R.id.tv_activity_title_two)
    TextView tvActivityTitleTwo;
    @BindView(R.id.tv_activity_content_two)
    TextView tvActivityContentTwo;
    @BindView(R.id.tv_activity_big_two)
    TextView tvActivityBigTwo;
    @BindView(R.id.tv_activity_big_unit_two)
    TextView tvActivityBigUnitTwo;
    @BindView(R.id.ll_activity_two)
    LinearLayout llActivityTwo;
    @BindView(R.id.tv_activity_title_three)
    TextView tvActivityTitleThree;
    @BindView(R.id.tv_activity_content_three)
    TextView tvActivityContentThree;
    @BindView(R.id.tv_activity_big_three)
    TextView tvActivityBigThree;
    @BindView(R.id.tv_activity_big_unit_three)
    TextView tvActivityBigUnitThree;
    @BindView(R.id.ll_activity_three)
    LinearLayout llActivityThree;
    @BindView(R.id.ll_activity)
    LinearLayout llActivity;
    @BindView(R.id.rv_insurance_type)
    RecyclerView rvInsuranceType;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rv_insurance)
    RecyclerView rvInsurance;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_insurance;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("保险专区");
        GlideUtils.loadImage(mContext,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562564033158&di=1b9c4740cb43227b62fdec1020534c62&imgtype=0&src=http%3A%2F%2Fres.vobao.com%2Fres1%2F201407%2F0822%2Fpsb_790898953781786.png",imgBanner);
        initRv();
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        List<String> list = new ArrayList<>();
        list.add("车险");
        list.add("健康");
        list.add("人寿");
        list.add("意外");
        list.add("旅行");
        rvInsuranceType.setLayoutManager(new GridLayoutManager(this,5));
        //禁用列表的滑动，使其跟随ScrollView
        rvInsurance.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //初始化适配器
        rvInsurance.setAdapter( new InsuranceAdapter(R.layout.item_insurance,list));
        InsuranceTypeAdapter insuranceTypeAdapter =  new InsuranceTypeAdapter(R.layout.item_insurance_type,list);
        rvInsuranceType.setAdapter(insuranceTypeAdapter);
        insuranceTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext,InsuranceDetailActivity.class).putExtra("title","保险"));
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



    @OnClick({R.id.ll_toolbar_left, R.id.ll_activity_one, R.id.ll_activity_two,
            R.id.ll_activity_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //活动1
            case R.id.ll_activity_one:
                break;
                //活动2
            case R.id.ll_activity_two:
                break;
                //活动3
            case R.id.ll_activity_three:
                break;
        }
    }
    //保险分类
    class InsuranceTypeAdapter extends BaseRecycleNewAdapter<String>{

        public InsuranceTypeAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_tv_insurance_type,item);
        }
    }

    //保险列表
    class InsuranceAdapter extends BaseRecycleNewAdapter<String>{

        public InsuranceAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView img = helper.getView(R.id.item_img_photo);
            GlideUtils.loadRoundCircleImage(mContext,"http://img0.imgtn.bdimg.com/it/u=3315130648,1748189790&fm=26&gp=0.jpg",img);
        }
    }
}
