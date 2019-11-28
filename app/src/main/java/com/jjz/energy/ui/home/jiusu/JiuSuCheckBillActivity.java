package com.jjz.energy.ui.home.jiusu;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu.BillContentEntry;
import com.jjz.energy.presenter.jiusu.BillPresenter;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IBillView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 核对发票
 * @author: create by chenhao on 2019/7/3
 */
public class JiuSuCheckBillActivity extends BaseActivity<BillPresenter> implements IBillView {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_bill_title)
    TextView tvBillTitle;
    @BindView(R.id.tv_bill_type)
    TextView tvBillType;
    @BindView(R.id.tv_bill_content)
    TextView tvBillContent;
    @BindView(R.id.tv_bill_money)
    TextView tvBillMoney;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_identifier)
    TextView tvIdentifier;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;
    @BindView(R.id.tv_submi)
    TextView tvSubmi;

    @Override
    protected BillPresenter getPresenter() {
        return new BillPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_check_bill;
    }

    /**
     * 订单id
     */
    private String order_id;
    private BillContentEntry data;
    @Override
    protected void initView() {
        tvToolbarTitle.setText("核对发票信息");
        data = (BillContentEntry) getIntent().getSerializableExtra("data");
        order_id = data.getOrder_id();
        tvBillTitle.setText(data.getTitle());
        tvBillType.setText(data.getType());
        tvBillContent.setText(data.getContent());
        tvBillMoney.setText(data.getMoney());
        tvTime.setText(data.getTime());
        tvIdentifier.setText(data.getIdentifier());
        tvAddress.setText(data.getAddress());
        tvPhone.setText(data.getPhone());
        tvBank.setText(data.getBank());
        tvBankNumber.setText(data.getBank_number());
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_submi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_submi:
                //提交
                HashMap<String, String> map = new HashMap<>();
                //电子发票
                map.put("invoice_type","1");
                //订单id
                map.put("order_id",order_id);
                //1个人/2企业
                map.put("title_type",data.getIsBillUnit()+"");
                //发票内容
                map.put("invoice_desc",data.getContent());
                //发票金额
                map.put("invoice_money",data.getMoney());
                //抬头
                map.put("invoice_title",data.getTitle());
                if (data.getIsBillUnit()==2){
                    //识别号
                    map.put("taxpayer",data.getIdentifier());
                    //地址
                    map.put("address",data.getAddress());
                    //电话
                    map.put("invoice_mobile", data.getPhone());
                    //银行
                    map.put("invoice_bank", data.getBank());
                    //银行卡号
                    map.put("invoice_account", data.getBank_number());
                }
                mPresenter.submitBillData(PacketUtil.getRequestPacket(map));
                break;
        }
    }

    @Override
    public void isBillSubmitSuccess(String data) {
        showToast("申请成功");
        ActivityUtils.finishActivity(JiuSuBillApplicationActivity.class);
        finish();
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }
}
