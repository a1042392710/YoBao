package com.jjz.energy.ui.mine.information;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.widgets.address_select.AddressSelector;
import com.jjz.energy.widgets.address_select.BottomDialog;
import com.jjz.energy.widgets.address_select.OnAddressSelectedListener;
import com.jjz.energy.widgets.address_select.bean.City;
import com.jjz.energy.widgets.address_select.bean.County;
import com.jjz.energy.widgets.address_select.bean.Province;
import com.jjz.energy.widgets.address_select.bean.Street;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 操作收货地址 添加或者修改
 * @author: create by chenhao on 2019/9/3
 */
public class UpdateAddressActivity extends BaseActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_recipients)
    EditText etRecipients;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.et_detailed_address)
    EditText etDetailedAddress;
    @BindView(R.id.switch_is_defult)
    SwitchCompat switchIsDefult;

    @Override
    protected BasePresenter getPresenter() {

        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_update_address;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("地址信息");
        tvToolbarRight.setText("保存");
        tvToolbarRight.setTextColor(getResources().getColor(R.color.colorAccent));
        //初始化地址选择器
        initBottomSelect();
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
        dialog.setTextSelectedColor(R.color.text_black33);//设置字体获得焦点的颜色
        dialog.setTextUnSelectedColor(R.color.color_ff4800);//设置字体没有获得焦点的颜色
        dialog.setSelectorAreaPositionListener(this);
//        if (mAddressBean!=null){
//            //设置默认省市区
//            try{
//                dialog.setDisplaySelectorArea(mAddressBean.getProvince()+"",mAddressBean.getCity()+"",mAddressBean.getDistrict()+"","");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }



    //底部弹出省市区县选择器
    private BottomDialog dialog;
    @OnClick({R.id.ll_toolbar_left, R.id.tv_select_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //选择详细地址
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
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }
}
