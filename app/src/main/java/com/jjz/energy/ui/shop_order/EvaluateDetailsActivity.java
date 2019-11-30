package com.jjz.energy.ui.shop_order;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.order.EvaluateDetailsBean;
import com.jjz.energy.presenter.order.EvaluatePresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.order.IEvaluateView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 查看评价
 * @author: create by chenhao on 2019/10/26
 */
public class EvaluateDetailsActivity extends BaseActivity<EvaluatePresenter> implements IEvaluateView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_commodity_title)
    TextView tvCommodityTitle;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.img_her_head)
    ImageView imgHerHead;
    @BindView(R.id.tv_her_name)
    TextView tvHerName;
    @BindView(R.id.tv_her_evaluate)
    TextView tvHerEvaluate;
    @BindView(R.id.tv_her_evaluate_time)
    TextView tvHerEvaluateTime;
    @BindView(R.id.img_her_evaluate)
    ImageView imgHerEvaluate;
    @BindView(R.id.img_her_evaluate_mark)
    ImageView imgHerEvaluateMark;
    @BindView(R.id.rv_her)
    RelativeLayout rvHer;
    @BindView(R.id.img_my_head)
    ImageView imgMyHead;
    @BindView(R.id.tv_my_name)
    TextView tvMyName;
    @BindView(R.id.tv_my_evaluate)
    TextView tvMyEvaluate;
    @BindView(R.id.tv_my_evaluate_time)
    TextView tvMyEvaluateTime;
    @BindView(R.id.img_my_evaluate)
    ImageView imgMyEvaluate;
    @BindView(R.id.img_my_evaluate_mark)
    ImageView imgMyEvaluateMark;
    @BindView(R.id.rv_mine)
    RelativeLayout rvMine;
    @BindView(R.id.tv_evaluate_toast)
    TextView tvEvaluateToast;
    /**
     * 订单编号
     */
    private String order_sn;



    @Override
    protected void initView() {
        order_sn = getIntent().getStringExtra(Constant.ORDER_SN );
        tvToolbarTitle.setText("评价详情");
        mPresenter.getEvaluateDetails(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
    }


    @Override
    public void isGetEvaluateDetailsInfoSuc(EvaluateDetailsBean data) {
        //买家有评价
        if (!StringUtil.isEmpty(data.getBuyer_content())){
            rvHer.setVisibility(View.VISIBLE);
            GlideUtils.loadRoundCircleImage(mContext,data.getGoods_info().getGoods_images(),imgCommodity);
            tvCommodityTitle.setText(data.getGoods_info().getGoods_name());
            tvOrderTime.setText("交易成功时间 "+ DateUtil.longToDate(data.getGoods_info().getConfirm_time(),null));
            tvHerEvaluate.setText(data.getBuyer_content());
            tvHerName.setText(data.getBuyer_nickname());
            tvHerEvaluateTime.setText(DateUtil.longToDate(data.getBuyer_add_time(),null));
            GlideUtils.loadCircleImage(mContext,data.getBuyer_head_pic(),imgHerHead);
            //评论的图
            if (!StringUtil.isEmpty(data.getBuyer_img())){
                imgHerEvaluate.setVisibility(View.VISIBLE);
                GlideUtils.loadImage(mContext,data.getBuyer_img(),imgHerEvaluate);
            }
            //评价
            setStart(0,data.getBuyer_start());
        }
        //卖家有评价
        if (!StringUtil.isEmpty(data.getSaler_content())){
            rvMine.setVisibility(View.VISIBLE);
            tvMyEvaluate.setText(data.getSaler_content());
            tvMyName.setText(data.getSaler_nickname());
            tvMyEvaluateTime.setText(DateUtil.longToDate(data.getSaler_add_time(),null));
            //头像
            GlideUtils.loadCircleImage(mContext,data.getSaler_head_pic(),imgMyHead);
            //评论的图
            if (!StringUtil.isEmpty(data.getSaler_img())){
                imgMyEvaluate.setVisibility(View.VISIBLE);
                GlideUtils.loadImage(mContext,data.getSaler_img(),imgMyEvaluate);
            }
            //评价
            setStart(1,data.getSaler_start());
        }

    }

    /**
     * @param type 0 买家  1 卖家
     */
    private void setStart(int type, int start) {

        switch (start) {
            //差
            case 1:
                if (type==0){
                    imgHerEvaluateMark.setImageDrawable(getResources().getDrawable(R.mipmap.ic_rb_bad_checked));
                }else{
                    imgMyEvaluateMark.setImageDrawable(getResources().getDrawable(R.mipmap.ic_rb_bad_checked));
                }
                break;
            //一般
            case 2:
                if (type==0){
                    imgHerEvaluateMark.setImageDrawable(getResources().getDrawable(R.mipmap.ic_rb_ordinary_checked));
                }else{
                    imgMyEvaluateMark.setImageDrawable(getResources().getDrawable(R.mipmap.ic_rb_ordinary_checked));
                }
                break;
            //好
            case 3:
                if (type==0){
                    imgHerEvaluateMark.setImageDrawable(getResources().getDrawable(R.mipmap.ic_rb_good_checked));
                }else{
                    imgMyEvaluateMark.setImageDrawable(getResources().getDrawable(R.mipmap.ic_rb_good_checked));
                }
                break;


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

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    protected EvaluatePresenter getPresenter() {
        return new EvaluatePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_evaluate_details;
    }


    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}
