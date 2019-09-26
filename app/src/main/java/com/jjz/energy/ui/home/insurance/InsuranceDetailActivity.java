package com.jjz.energy.ui.home.insurance;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
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
 * @Features: 保险列表
 * @author: create by chenhao on 2019/7/9
 */
public class InsuranceDetailActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_insurance)
    RecyclerView rvInsurance;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_insurance_detail;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        tvToolbarTitle.setText(getIntent().getStringExtra("title")+"");
        initRv();

    }
    //初始化列表
    private void initRv() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        //禁用列表的滑动，使其跟随ScrollView
        rvInsurance.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //初始化适配器
        rvInsurance.setAdapter( new InsuranceAdapter(R.layout.item_insurance,list));

    }

    //保险列表
    class InsuranceAdapter extends BaseRecycleNewAdapter<String> {

        public InsuranceAdapter(int layoutResId, @Nullable List<String> data) {
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
