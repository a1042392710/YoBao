package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.List;

public  class AddressManagerAdapter extends BaseRecycleNewAdapter<String> {

        public AddressManagerAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder helper, String item) {
//            String addressStr = item.getArea()+item.getAddress();
//            //地址
//            helper.setText(R.id.item_tv_address, addressStr.replace(" ", ""));
//            //电话
//            helper.setText(R.id.item_tv_consignee_phone, item.getMobile());
//            //是否默认
//            if (item.getIs_default()==1){
//                helper.setVisible(R.id.item_tv_is_default, true);
//            }else{
//                helper.setVisible(R.id.item_tv_is_default, false);
//            }
//            //收货人
//            helper.setText(R.id.item_tv_consignee_name, item.getConsignee());
//            helper.getView(R.id.item_img_modify_address).setOnClickListener(v -> {
//                // 编辑地址
//                startActivityForResult(new Intent(mContext,AddAddressActivity.class)
//                        .putExtra(Constant.INTENT_KEY_OBJECT, mList.get(helper.getLayoutPosition()))
//                        .putExtra("title","编辑地址" ),10);
//            });
        }
    }