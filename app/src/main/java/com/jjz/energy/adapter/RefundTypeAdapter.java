package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.order.RefundTypeBean;

import java.util.List;

/**
 * @author chenhao 2019/1/8
 * @function 退换货类型/原因
 */
public class RefundTypeAdapter extends BaseRecycleNewAdapter<RefundTypeBean> {

    /**
     * 选中的下标
     */
    public  int selectId = 1 ;
    /**
     * 选中的文字
     */
    public  String selectStr = "";

    public RefundTypeAdapter(int layoutResId, @Nullable List<RefundTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundTypeBean item) {
        helper.setText(R.id.item_tv_content, item.getValue());
        if (item.isSelect()){
            selectId = item.getId();
            selectStr = item.getValue();
            helper.setImageResource(R.id.pop_img_one, R.mipmap.ic_select_rb);
        }else{
            helper.setImageResource(R.id.pop_img_one, R.mipmap.ic_unselect_rb);
        }
        //点击子项设置选中
        helper.getView(R.id.pop_ll_one).setOnClickListener(v ->{
            for (int i = 0; i < mData.size(); i++) {
                mData.get(i).setSelect(false);
            }
            mData.get(helper.getLayoutPosition()).setSelect(true);
            //刷新数据
            notifyDataSetChanged();
        });
    }

    //返回选中的Id
    public  int  getSelectId(){
        return selectId;
    }
    //返回选中的文字
    public  String  getSelectStr(){
        return selectStr;
    }
}
