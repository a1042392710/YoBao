package com.jjz.energy.util;

import android.content.Context;

import com.jjz.energy.R;
import com.jjz.energy.widgets.address_select.AddressSelector;
import com.jjz.energy.widgets.address_select.BottomDialog;
import com.jjz.energy.widgets.address_select.OnAddressSelectedListener;
import com.jjz.energy.widgets.address_select.bean.City;
import com.jjz.energy.widgets.address_select.bean.County;
import com.jjz.energy.widgets.address_select.bean.Province;
import com.jjz.energy.widgets.address_select.bean.Street;

/**
 * @author chenhao 2018/12/18
 * @function 地址选择器
 */
public class BottomDialogUtil implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener   {

    //底部弹出省市区县选择器
    private BottomDialog dialog;
    private OnSelectListener mOnSelectListener;

    public BottomDialog showBottomDialog(Context context,OnSelectListener mOnSelectListener) {
        this.mOnSelectListener =mOnSelectListener;
        //选择收货地址
        if (dialog == null) {
            dialog = new BottomDialog(context);
            dialog.setOnAddressSelectedListener(this);
            dialog.setDialogDismisListener(this);
            dialog.setTextSize(14);//设置字体的大小
            dialog.setIndicatorBackgroundColor(R.color.red_fe8977);//设置指示器的颜色
            dialog.setTextSelectedColor(R.color.text_black28);//设置字体获得焦点的颜色
            dialog.setTextUnSelectedColor(R.color.red_fe8977);//设置字体没有获得焦点的颜色
            dialog.setSelectorAreaPositionListener(this);
        }
        dialog.show();
        return dialog;
    }
    //默认省市区
    public BottomDialog createBottomDialog(Context context,String p ,String c, String d ,OnSelectListener mOnSelectListener) {
        this.mOnSelectListener =mOnSelectListener;
        //选择收货地址
        if (dialog == null) {
            dialog = new BottomDialog(context);
            dialog.setOnAddressSelectedListener(this);
            dialog.setDialogDismisListener(this);
            dialog.setTextSize(14);//设置字体的大小
            dialog.setIndicatorBackgroundColor(R.color.red_fe8977);//设置指示器的颜色
            dialog.setTextSelectedColor(R.color.text_black28);//设置字体获得焦点的颜色
            dialog.setTextUnSelectedColor(R.color.red_fe8977);//设置字体没有获得焦点的颜色
            dialog.setSelectorAreaPositionListener(this);

            //设置默认省市区
            try{
                dialog.setDisplaySelectorArea(p,c,d,"");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dialog;
    }

    @Override
    public void dialogclose() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition, int streetPosition) {
        if (mOnSelectListener != null) {
            mOnSelectListener.selectorAreaPosition(provincePosition, cityPosition, countyPosition, streetPosition);
        }
    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        if (mOnSelectListener != null) {
            dialogclose();
            mOnSelectListener.onAddressSelected(province, city, county, street);
        }
    }

    public interface OnSelectListener {
        void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition,
                                  int streetPosition);
        void onAddressSelected(Province province, City city, County county, Street street);
    }

}
