package com.jjz.energy.ui.mine.shipping_address;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.AddressBean;
import com.jjz.energy.presenter.mine.AddAddressPresenter;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IAddAddressView;
import com.jjz.energy.widgets.address_select.AddressSelector;
import com.jjz.energy.widgets.address_select.BottomDialog;
import com.jjz.energy.widgets.address_select.OnAddressSelectedListener;
import com.jjz.energy.widgets.address_select.bean.City;
import com.jjz.energy.widgets.address_select.bean.County;
import com.jjz.energy.widgets.address_select.bean.Province;
import com.jjz.energy.widgets.address_select.bean.Street;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenhao 2018/12/7
 * @function 添加收货地址/编辑收货地址
 * 使用须知：进入该页面 需要传递的参数有：1、title 标题 2 AddressBean 如果为修改地址，则需要传递该对象
 */
public class AddAddressActivity extends BaseActivity<AddAddressPresenter> implements IAddAddressView, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.ck_all_checked)
    CheckBox ckAllChecked;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    //收件人
    @BindView(R.id.et_recipients)
    EditText etRecipients;
    //电话
    @BindView(R.id.et_phone)
    EditText etPhone;
    //选择地区
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    //详细地址
    @BindView(R.id.et_detailed_address)
    EditText etDetailedAddress;
    /**
     * 地址列表传来的数据 有则需要填入输入框
     */
    private AddressBean.ListBean mAddressBean;

    @Override
    protected AddAddressPresenter getPresenter() {
        return new AddAddressPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_add_address;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        tvToolbarTitle.setText(title);
        tvToolbarRight.setText("删除");
        //有数据就表示为编辑状态，无数据表示为添加状态
        mAddressBean = (AddressBean.ListBean) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        //添加状态
        if (mAddressBean == null) {
            tvToolbarRight.setVisibility(View.GONE);
        } else {
            //修改状态
            tvToolbarRight.setVisibility(View.VISIBLE);
            //给输入框赋值
            setAddressData();
        }
        //初始化地址选择器
        initBottomSelect();
    }


    /**
     * 给输入框赋值
     */
    private void setAddressData() {
        //收件人
        etRecipients.setText(mAddressBean.getConsignee());
        //电话
        etPhone.setText(mAddressBean.getMobile());
        //详细地址
        etDetailedAddress.setText(mAddressBean.getAddress());
        //默认
        if (1==mAddressBean.getIs_default()){
            ckAllChecked.setChecked(true);
        }else{
            ckAllChecked.setChecked(false);
        }
    }


    /**
     * 添加/修改地址 提交数据
     */
    private void addSubmit(){
        if (StringUtil.isEmpty(etRecipients.getText().toString())){
            showToast("请填写收件人");
            return;
        }
        if (StringUtil.isEmpty(etPhone.getText().toString())){
            showToast("请填写手机号");
            return;
        }
        if (StringUtil.isEmpty(etPhone.getText().toString())){
            showToast("请填写详细地址");
            return;
        }
        if (StringUtil.isEmpty(tvSelectAddress.getText().toString())){
            showToast("请选择所在地区");
            return;
        }
        //赋值
        Map<String,String>map = new HashMap<>();
        //编辑状态
        if (mAddressBean!=null){
            map.put("address_id",mAddressBean.getAddress_id()+"");
            if (provinceCode==0){
                provinceCode = mAddressBean.getProvince();
            }
            if (cityCode ==0){
                cityCode = mAddressBean.getCity();
            }
            if (countyCode ==0){
                countyCode = mAddressBean.getCountry();
            }
        }
        //收货人
        map.put("consignee", etRecipients.getText().toString().trim());
        //省Id
        map.put("province", provinceCode + "");
        //市Id
        map.put("city", cityCode + "");
        //区Id
        map.put("district", countyCode + "");
        //详细地址
        map.put("address", etDetailedAddress.getText().toString().trim());
        //手机号
        map.put("mobile", etPhone.getText().toString().trim());
        //是否默认
        if (ckAllChecked.isChecked()){
            map.put("is_default", "1");
        }else{
            map.put("is_default", "0");
        }
        mPresenter.addAndModifyAddress(PacketUtil.getRequestPacket(map));
    }

    /**
     * 初始化地址选择器
     */
    private void initBottomSelect(){
        dialog = new BottomDialog(this);
        dialog.setOnAddressSelectedListener(this);
        dialog.setDialogDismisListener(this);
        dialog.setTextSize(14);//设置字体的大小
        dialog.setIndicatorBackgroundColor(R.color.color_ff4800);//设置指示器的颜色
        dialog.setTextSelectedColor(R.color.text_black28);//设置字体获得焦点的颜色
        dialog.setTextUnSelectedColor(R.color.color_ff4800);//设置字体没有获得焦点的颜色
        dialog.setSelectorAreaPositionListener(this);
        if (mAddressBean!=null){
            //设置默认省市区
            try{
                dialog.setDisplaySelectorArea(mAddressBean.getProvince()+"",mAddressBean.getCity()+"",mAddressBean.getDistrict()+"","");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //底部弹出省市区县选择器
    private BottomDialog dialog;
    @OnClick({R.id.ll_toolbar_left, R.id.tv_ok, R.id.tv_toolbar_right,R.id.tv_select_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_toolbar_right:
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您确定删除当前收货地址吗？", () -> {
                    mPresenter.deleteAddress(PacketUtil.getRequestPacket(Utils.stringToMap("address_id", mAddressBean.getAddress_id() + "")));
                });
                break;
            case R.id.tv_ok:
                addSubmit();
                break;
            case R.id.tv_select_address:
                //选择收货地址
                dialog.show();
                break;
        }
    }


    @Override
    public void dialogclose() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    private int provinceCode = 0;
    private int cityCode = 0;
    private int countyCode = 0;

    @Override
    public void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition, int streetPosition) { }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        provinceCode = province.id;
        cityCode = city.id;
        countyCode = county.id;

        String s = (province == null ? "" : province.name) + "  " + (city == null ? "" : city.name) + "  " + (county == null ? "" : county.name);
        tvSelectAddress.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void isAddSuccess(AddressBean data) {
        showToast("操作成功");
        setResult(20);
        finish();
    }

    @Override
    public void isFail(String msg ,boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    public void isDeleteSuccess(AddressBean data) {
        showToast("删除成功");
        setResult(20);
        finish();
    }


    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }
}
