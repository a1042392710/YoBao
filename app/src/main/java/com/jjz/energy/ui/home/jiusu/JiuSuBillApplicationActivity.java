package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.BillContentEntry;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenhao 2018/12/25
 * @function 发票申请
 */
public class JiuSuBillApplicationActivity extends BaseActivity {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_bill_title)
    TextView tvBillTitle;
    @BindView(R.id.tv_bill_money)
    TextView tvBillMoney;
    @BindView(R.id.rb_bill_unit)
    RadioButton rbBillUnit;
    @BindView(R.id.rb_bill_person)
    RadioButton rbBillPerson;
    @BindView(R.id.rg_bill_type)
    RadioGroup rgBillType;
    @BindView(R.id.et_bill_unit_title)
    EditText etBillUnitTitle;
    @BindView(R.id.et_bill_unit_identifier)
    EditText etBillUnitIdentifier;
    @BindView(R.id.et_unit_address)
    EditText etUnitAddress;
    @BindView(R.id.et_unit_phone)
    EditText etUnitPhone;
    @BindView(R.id.et_unit_bank)
    EditText etUnitBank;
    @BindView(R.id.et_unit_bank_card)
    EditText etUnitBankCard;
    @BindView(R.id.ll_look_all)
    LinearLayout llLookAll;
    @BindView(R.id.cb_look_all)
    CheckBox cbLookAll;
    @BindView(R.id.ll_unit_info)
    LinearLayout llUnitInfo;
    @BindView(R.id.et_bill_person_title)
    EditText etBillPersonTitle;
    @BindView(R.id.ll_person_info)
    LinearLayout llPersonInfo;
    @BindView(R.id.tv_next)
    TextView tvNext;
    //发票的选择 ture 企业  false 个人
    private boolean isBillUnitl = true;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_bill_applicationl;
    }

    private String billMoney;
    private String order_id;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("申请发票");
          billMoney = getIntent().getStringExtra("money");
          if (StringUtil.isEmpty(billMoney)){
              billMoney="";
          }
          tvBillMoney.setText(billMoney+"元");
        order_id = getIntent().getStringExtra("order_id");
        if (StringUtil.isEmpty(order_id)){
            order_id="";
        }
        //是否填写更多
        cbLookAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //选中
            if (isChecked){
                llLookAll.setVisibility(View.VISIBLE);
            }else {
                llLookAll.setVisibility(View.GONE);
            }
        });
        //控制企业和个人的填写信息显示隐藏
        rgBillType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId== R.id.rb_bill_unit){
                isBillUnitl = true;
                llPersonInfo.setVisibility(View.GONE);
                llUnitInfo.setVisibility(View.VISIBLE);
            }else{
                isBillUnitl=false;
                llPersonInfo.setVisibility(View.VISIBLE);
                llUnitInfo.setVisibility(View.GONE);
            }
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

    @OnClick({R.id.ll_toolbar_left, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //下一步
            case R.id.tv_next:
                if (isSubmit()){
                    BillContentEntry data = new BillContentEntry();
                    //订单id
                    data.setOrder_id(order_id);
                    data.setType("电子发票");
                    data.setContent("汽车用品");
                    data.setMoney(billMoney);
                    data.setTime(DateUtil.longToDate(System.currentTimeMillis(),null));
                    //企业发票就跳转审核
                    if (isBillUnitl){
                        data.setIsBillUnit(2);
                        //整理出所有的数据，传入下一个页面
                        data.setTitle(etBillUnitTitle.getText().toString());
                        data.setIdentifier(etBillUnitIdentifier.getText().toString());
                        data.setAddress(etUnitAddress.getText().toString());
                        data.setPhone(etUnitPhone.getText().toString());
                        data.setBank(etUnitBank.getText().toString());
                        data.setBank_number(etUnitBankCard.getText().toString());

                    }else{
                        data.setIsBillUnit(1);
                        data.setTitle(etBillPersonTitle.getText().toString());
                    }
                    Intent intent = new Intent(mContext, JiuSuCheckBillActivity.class);
                    intent.putExtra("data",data);
                    startActivity(intent);

                }
                break;
        }
    }

    /**
     * 提交前的检测
     */
    private boolean isSubmit(){
        //选择的公司
        if (isBillUnitl){
            if (StringUtil.isEmpty(etBillUnitTitle.getText().toString())){
                showToast("请填写发条抬头");
                return false;
            }
            if (StringUtil.isEmpty(etBillUnitIdentifier.getText().toString())){
                showToast("请填写纳税人识别号");
                return false;
            }
        }else{
            //选择的个人
            if (StringUtil.isEmpty(etBillPersonTitle.getText().toString())){
                showToast("请填写姓名");
                return false;
            }
        }
        return true;
    }


}
