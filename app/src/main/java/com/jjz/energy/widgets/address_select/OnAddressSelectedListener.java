package com.jjz.energy.widgets.address_select;


import com.jjz.energy.widgets.address_select.bean.City;
import com.jjz.energy.widgets.address_select.bean.County;
import com.jjz.energy.widgets.address_select.bean.Province;
import com.jjz.energy.widgets.address_select.bean.Street;

public interface OnAddressSelectedListener {
    void onAddressSelected(Province province, City city, County county, Street street);
}
