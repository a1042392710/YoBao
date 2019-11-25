package com.jjz.energy.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingDetailsBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 店内消费记录详情
 * @author: create by chenhao on 2019/11/25
 */
@RuntimePermissions
public class MineJiuSuShoppingDetailsActivity extends BaseActivity<JiuSuShopPresenter> implements IJiuSuShopView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_location_content)
    TextView tvLocationContent;
    @BindView(R.id.tv_location_distance)
    TextView tvLocationDistance;
    @BindView(R.id.tv_call_shop_phone)
    TextView tvCallShopPhone;
    @BindView(R.id.img_order_vcode)
    ImageView imgOrderVcode;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_order_phone)
    TextView tvOrderPhone;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_old_money)
    TextView tvOrderOldMoney;
    @BindView(R.id.tv_order_new_money)
    TextView tvOrderNewMoney;
    @BindView(R.id.tv_order_vcode_sn)
    TextView tvOrderVcodeSn;
    /**
     * 消费id
     */
    private int id;
    /**
     * 将数据存下来
     */
    private JiuSuShoppingDetailsBean mDetailsBean;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("消费详情");
        id = getIntent().getIntExtra("id", 0);
        mPresenter.getJiuSuShoppingDetails(PacketUtil.getRequestPacket(Utils.stringToMap("id",
                "id")));
    }

    @Override
    public void isGetJiusuShoppingDetailsSuc(JiuSuShoppingDetailsBean data) {
        mDetailsBean = data;
        //给所有的数据赋值
        tvShopName.setText("商家名称");
        tvLocationContent.setText("商家位置");
        tvLocationDistance.setText("距离你多远");
        tvOrderSn.setText("订单编号：");
        tvOrderPhone.setText("手机号：");
        tvOrderTime.setText("支付时间：");
        tvOrderOldMoney.setText("总价：");
        tvOrderNewMoney.setText("实付：");

    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_call_shop_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //打电话给商家
            case R.id.tv_call_shop_phone:
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您将拨打电话 123456789 ", () -> {
                    callPhone();
                });
                break;
        }
    }

    @Override
    protected JiuSuShopPresenter getPresenter() {
        return new JiuSuShopPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_jiusu_shopping_details;
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

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callPhone() {
        if (mDetailsBean!=null) {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            String number = mDetailsBean.getPhone_number();
            Uri data = Uri.parse("tel:" + number); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
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
        MineJiuSuShoppingDetailsActivityPermissionsDispatcher.onRequestPermissionsResult(this,
                requestCode, grantResults);
    }
}
