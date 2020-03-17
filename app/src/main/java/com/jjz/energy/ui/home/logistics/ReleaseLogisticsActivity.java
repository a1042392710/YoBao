package com.jjz.energy.ui.home.logistics;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.presenter.home.logistics.LogisticsPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.home.ILogisticsView;
import com.jjz.energy.widgets.datepicker.CustomDatePicker;

import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 发布物流
 * @author: create by chenhao on 2019/7/1
 */
public class ReleaseLogisticsActivity extends BaseActivity <LogisticsPresenter>implements ILogisticsView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_location_start)
    TextView tvLocationStart;
    @BindView(R.id.tv_location_end)
    TextView tvLocationEnd;
    @BindView(R.id.et_goods_name)
    EditText etGoodsName;
    @BindView(R.id.et_goods_money)
    EditText etGoodsMoney;
    @BindView(R.id.et_goods_weight)
    EditText etGoodsWeight;
    @BindView(R.id.et_goods_volume)
    EditText etGoodsVolume;
    @BindView(R.id.et_goods_desc)
    EditText etGoodsDesc;
    @BindView(R.id.et_sender)
    EditText etSender;
    @BindView(R.id.et_sender_phone)
    EditText etSenderPhone;
    @BindView(R.id.et_receiver)
    EditText etReceiver;
    @BindView(R.id.et_receiver_phone)
    EditText etReceiverPhone;
    @BindView(R.id.cv_release)
    CardView cvRelease;
    @BindView(R.id.et_price)
    EditText etPrice;
    /**
     * 时间选择器
     */
    private CustomDatePicker mCustomDatePicker;

    @Override
    protected LogisticsPresenter getPresenter() {
        return new LogisticsPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_release_logistics;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("发布物流");
        //初始化时间选择器 设置开始时间  和结束时间
        Date startDate =  new Date();
        Date endDate = new Date();
        endDate.setYear(startDate.getYear()+1);
        mCustomDatePicker = new CustomDatePicker(this,startDate,endDate, time -> {
            tvTimeStart.setText(time);
        });
        mCustomDatePicker.showSpecificTime(true); // 不显示时和分
        mCustomDatePicker.setIsLoop(false); // 不允许循环滚动

    }




    @OnClick({R.id.ll_toolbar_left, R.id.tv_time_start, R.id.tv_location_start,
            R.id.tv_location_end, R.id.cv_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_toolbar_left:
                finish();
                break;

            //选择发货时间
            case R.id.tv_time_start:
                if (StringUtil.isEmpty(tvTimeStart.getText().toString()) || "请选择".equals(tvTimeStart.getText().toString())) {
                    mCustomDatePicker.showNow();
                } else {
                    mCustomDatePicker.show(tvTimeStart.getText().toString());
                }
                break;

                //选择起点
            case R.id.tv_location_start:
                startActivityForResult(new Intent(mContext,MapSelectActivity.class).putExtra("type","start"),0);
                break;

                //选择终点
            case R.id.tv_location_end:
                startActivityForResult(new Intent(mContext,MapSelectActivity.class).putExtra("type","end"),0);
                break;

                //发布物流
            case R.id.cv_release:
            if (submitCheck()){
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您是否确认信息填写无误，确认发布吗？", () -> submit());
            }
                break;
        }
    }

    /**
     * 提交数据
     */
    private void submit() {
        HashMap<String,String>map = new HashMap<>();
        map.put("start_city",startLatLngBean.getCityname());
        map.put("start_address",startLatLngBean.getPoiaddress());
        map.put("start_poiname",startLatLngBean.getPoiname());
        map.put("start_lat",String.valueOf(startLatLngBean.getLat()));
        map.put("start_lng",String.valueOf(startLatLngBean.getLng()));
        map.put("end_city",endLatLngBean.getCityname());
        map.put("end_address",endLatLngBean.getPoiaddress());
        map.put("end_poiname",endLatLngBean.getPoiname());
        map.put("end_lat",String.valueOf(endLatLngBean.getLat()));
        map.put("end_lng",String.valueOf(endLatLngBean.getLng()));
        map.put("goods_name",etGoodsName.getText().toString());
        map.put("weight",etGoodsWeight.getText().toString());
        map.put("price",etPrice.getText().toString());
        map.put("goods_price",etGoodsMoney.getText().toString());
        map.put("shipper",etSender.getText().toString());
        map.put("shipper_phone",etSenderPhone.getText().toString());
        map.put("consignee",etReceiver.getText().toString());
        map.put("consignee_phone",etReceiverPhone.getText().toString());
        map.put("start_time", DateUtil.dateToStampStr(tvTimeStart.getText().toString(),"yyyy-MM-dd HH:mm"));
        map.put("caption",etGoodsDesc.getText().toString());
        map.put("goods_size",etGoodsVolume.getText().toString());

        mPresenter.putLogisticsInfo(PacketUtil.getRequestPacket(map));
    }

    /**
     * 提交数据前的检查
     * @return
     */
    private boolean submitCheck() {
        if (null == startLatLngBean){
            showToast("请选择装货地点");
            return false;
        }
        if (null == endLatLngBean){
            showToast("请选择卸货地点");
            return false;
        }
        if (StringUtil.isEmpty(etGoodsName.getText().toString())){
            showToast("请输入货物名称");
            return false;
        }
        if (StringUtil.isEmpty(etPrice.getText().toString())){
            showToast("请输入期望运费");
            return false;
        }
        if (StringUtil.isEmpty(etGoodsWeight.getText().toString())){
            showToast("请输入货物重量");
            return false;
        }
        if (StringUtil.isEmpty(etSender.getText().toString())){
            showToast("请输入发货人名称");
            return false;
        }
        if (StringUtil.isEmpty(etSenderPhone.getText().toString())){
            showToast("请输入发货人手机号");
            return false;
        }

        if (StringUtil.isEmpty(etReceiver.getText().toString())){
            showToast("请输入收货人名称");
            return false;
        }
        if (StringUtil.isEmpty(etReceiverPhone.getText().toString())){
            showToast("请输入收货人手机号");
            return false;
        }

        return true;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }
    //起点和终点
    private MapSelectActivity.LatLngBean startLatLngBean;
    private MapSelectActivity.LatLngBean endLatLngBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0&&data!=null) {
            MapSelectActivity.LatLngBean bean =
                    (MapSelectActivity.LatLngBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
            startLatLngBean = bean;
            tvLocationStart.setText(startLatLngBean.getPoiname());
        } else if (resultCode == 1&&data!=null) {
            MapSelectActivity.LatLngBean bean =
                    (MapSelectActivity.LatLngBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
            endLatLngBean = bean;
            tvLocationEnd.setText(endLatLngBean.getPoiname());
        }
    }

    @Override
    public void isPutLogisticsInfoSuc(String data) {
            showToast("发布成功");
            finish();
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }
}
