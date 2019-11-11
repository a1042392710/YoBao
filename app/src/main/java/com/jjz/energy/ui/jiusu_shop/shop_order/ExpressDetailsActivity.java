package com.jjz.energy.ui.jiusu_shop.shop_order;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.order.ExpressTrackingBean;
import com.jjz.energy.presenter.order.ExpressPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.order.IExpressView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 物流详情
 * @author: create by chenhao on 2019/10/22
 */
public class ExpressDetailsActivity extends BaseActivity <ExpressPresenter> implements IExpressView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_express_company)
    TextView tvExpressCompany;
    @BindView(R.id.tv_express_no)
    TextView tvExpressNo;
    @BindView(R.id.tv_express_state)
    TextView tvExpressState;
    @BindView(R.id.rv_express)
    RecyclerView rvExpress;

    private ExpressAdapter mAdapter;


    /**
     * 物流单号
     */
    private String shipping_no;
    /**
     * 区分 是订单还是售后 有这个id 就表示售后物流
     */
    private String return_id;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("物流详情");
        shipping_no  = getIntent().getStringExtra("shipping_no");
        return_id  = getIntent().getStringExtra(Constant.RETURN_ID);
        rvExpress.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExpressAdapter(R.layout.item_express_details, new ArrayList<>());
        rvExpress.setAdapter(mAdapter);
        //查询物流信息
        HashMap<String,String> map = new HashMap<>();
        map.put("shipping_no",shipping_no);
        if (!StringUtil.isEmpty(return_id)){
            map.put(Constant.RETURN_ID,return_id);
        }
        mPresenter.getExpressTracking(PacketUtil.getRequestPacket(map));
    }

    @Override
    public void isGetExpressTrackingSuc(ExpressTrackingBean data) {
        //商品图片
        GlideUtils.loadRoundCircleImage(mContext,data.getGoods_images(),imgCommodity);
        //物流状态
        tvExpressState.setText(data.getState());
        //物流公司
        tvExpressCompany.setText(data.getShipping_name());
        //物流单号
        tvExpressNo.setText(data.getShipping_no());
        //刷新订单信息
        mAdapter.notifyChangeData(data.getTraces());
    }

    /**
     * 物流信息
     */
    class ExpressAdapter extends BaseRecycleNewAdapter<ExpressTrackingBean.Traces> {

        public ExpressAdapter(int layoutResId, @Nullable List<ExpressTrackingBean.Traces> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ExpressTrackingBean.Traces item) {
            //圆球
            ImageView item_img_cricle = helper.getView(R.id.item_img_cricle);
            //竖线
            View item_view_line = helper.getView(R.id.item_view_line);
            //标题
            TextView item_tv_title = helper.getView(R.id.item_tv_title);
            //时间
            TextView item_tv_time = helper.getView(R.id.item_tv_time);
            //第一个圆球和字是红色
            if (helper.getLayoutPosition() == 0) {
                item_tv_title.setTextColor(mContext.getResources().getColor(R.color.red_fe8977));
                item_img_cricle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_bg_cricle_orange));
            } else {
                item_tv_title.setTextColor(mContext.getResources().getColor(R.color.text_black88));
                item_img_cricle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_bg_cricle_gray));
            }
            //最后一条隐藏线
            item_view_line.setVisibility(helper.getLayoutPosition() == mData.size() - 1 ?
                    View.GONE : View.VISIBLE);
            //内容
            item_tv_title.setText(item.getAcceptStation());
            //时间
            item_tv_time.setText(item.getAcceptTime());

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

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    protected ExpressPresenter getPresenter() {
        return new ExpressPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_express_details;
    }


}
