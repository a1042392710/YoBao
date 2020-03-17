package com.jjz.energy.ui.home.logistics;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseWebActivity;
import com.jjz.energy.entry.home.LogisticsBean;
import com.jjz.energy.presenter.home.logistics.LogisticsPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.home.ILogisticsView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 物流详情
 * @author: create by chenhao on 2019/6/28
 */
@RuntimePermissions
public class LogisticsDetailActivity extends BaseActivity<LogisticsPresenter>implements ILogisticsView {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_time_info)
    TextView tvTimeInfo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_first_content)
    TextView tvFirstContent;
    @BindView(R.id.ll_first)
    LinearLayout llFirst;
    @BindView(R.id.item_ll_mark)
    LinearLayout itemLlMark;
    @BindView(R.id.item_tv_end)
    TextView itemTvEnd;
    @BindView(R.id.item_tv_end_content)
    TextView itemTvEndContent;
    @BindView(R.id.ll_end)
    LinearLayout llEnd;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_map)
    TextView tvMap;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.cv_weight)
    CardView cvWeight;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.cv_volume)
    CardView cvVolume;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_item_money)
    TextView tvItemMoney;
    @BindView(R.id.tv_item_desc)
    TextView tvItemDesc;
    @BindView(R.id.tv_sender)
    TextView tvSender;
    @BindView(R.id.tv_sender_phone)
    TextView tvSenderPhone;
    @BindView(R.id.img_sender_phone)
    ImageView imgSenderPhone;
    @BindView(R.id.tv_receiver)
    TextView tvReceiver;
    @BindView(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @BindView(R.id.img_receiver_phone)
    ImageView imgReceiverPhone;
    @BindView(R.id.cv_call_phone)
    CardView cvCallPhone;
    /**
     * 通过id 查询详情数据
     */
    private String id;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("订单详情");
        id= getIntent().getStringExtra("id");
        mPresenter.getLogisticsDetail(PacketUtil.getRequestPacket(Utils.stringToMap("id",id)));
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.img_sender_phone, R.id.img_receiver_phone,
            R.id.cv_call_phone ,R.id.tv_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //打电话给收货人
            case R.id.img_receiver_phone:
                if (mData!=null){
                 LogisticsDetailActivityPermissionsDispatcher.callPhoneWithCheck(this,mData.getConsignee_phone());
                }
                break;
            //打电话给货主
            case R.id.img_sender_phone:
            case R.id.cv_call_phone:
                if (mData!=null){
                    LogisticsDetailActivityPermissionsDispatcher.callPhoneWithCheck(this,mData.getShipper_phone());
                }
                break;
            //查看地图
            case R.id.tv_map:
                if (mData == null) {
                    showToast("数据异常");
                    return;
                }
               StringBuffer sb = new StringBuffer();
               sb.append("https://map.qq.com/nav/drive#routes/page?eword=");
               sb.append(mData.getEnd_address());
               sb.append("&epointx="+mData.getEnd_gcj_lng());
               sb.append("&epointy="+mData.getEnd_gcj_lat());
               sb.append("&sword="+mData.getStart_address());
               sb.append("&&spointx="+mData.getStart_gcj_lng());
               sb.append("&&spointy="+mData.getStart_gcj_lat());
               sb.append("&referer=myapp&key=B6RBZ-KY3CW-RAKR5-RVWHQ-UMILO-K7FJW&back=0");
                startActivity(new Intent(mContext, BaseWebActivity.class).putExtra(BaseWebActivity.WEB_TITLE,"生成路线").putExtra(BaseWebActivity.WEB_URL,sb.toString()));
                break;
        }
    }

    /**
     * 详情数据源
     */
    private LogisticsBean mData;

    @SuppressLint("SetTextI18n")
    @Override
    public void isGetLogisticsDetailSuc(LogisticsBean data) {
        mData = data;
        //获取详情数据成功 开始赋值
        tvTimeInfo.setText(DateUtil.longToDate(data.getStart_time(),"MM月dd日 HH时mm分"));
        tvTime.setText(DateUtil.getTimeFormatText(new Date(data.getAdd_time()*1000L))+"发布更新");
        tvFirst.setText(data.getStart_poiname());
        tvFirstContent.setText(data.getStart_address());
        itemTvEnd.setText(data.getEnd_poiname());
        itemTvEndContent.setText(data.getEnd_address());
        tvWeight.setText(data.getWeight());
        //体积
        if (!StringUtil.isEmpty(data.getGoods_size())){
            cvVolume.setVisibility(View.VISIBLE);
            tvVolume.setText(data.getGoods_size());
        }
        //重量
        tvWeight.setText(data.getWeight());
        //名称
        tvItemName.setText(data.getGoods_name());
        //货物价值
        tvItemMoney.setText(data.getGoods_price()+"元");
        //备注
        tvItemDesc.setText(data.getCaption());
        //发货人
        tvSender.setText(data.getShipper());
        //电话
        tvSenderPhone.setText(data.getShipper_phone());
        //收货人
        tvReceiver.setText(data.getConsignee());
        //电话
        tvReceiverPhone.setText(data.getConsignee_phone());
        //里程计算
        tvDistance.setText("里程最短约"+Utils.getDistance(data.getStart_gcj_lng(),data.getStart_gcj_lat(),data.getEnd_gcj_lng(),data.getEnd_gcj_lat())+"公里");
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    protected LogisticsPresenter getPresenter() {
        return new LogisticsPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_logistics_detail;
    }

    // =========================================== 权限申请


    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callPhone(String phone) {
        // 拨号：激活系统的拨号组件
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作

        Uri data = Uri.parse("tel:" + phone); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
    }
    //给用户解释为什么要申请权限
    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showCallPhone(final PermissionRequest request) {
        //唤起打电话权限
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有电话权限可不能打电话哦", () -> {
            request.proceed();//继续执行请求
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogisticsDetailActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode
                , grantResults);
    }
}
