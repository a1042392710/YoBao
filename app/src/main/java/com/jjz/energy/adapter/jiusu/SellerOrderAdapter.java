package com.jjz.energy.adapter.jiusu;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;
import com.jjz.energy.ui.home.jiusu.JiuSuMineSellerOrderFragment;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

/**
 * @Features: 卖家订单适配器
 * @author: create by chenhao on 2019/4/15
 */
public class SellerOrderAdapter extends BaseRecycleNewAdapter<JiuSuOrderBean.ListBean> {
    /**
     * 选中的接受订单的下标
     */
    private int mSelectPosition;

    private JiuSuMineSellerOrderFragment mFragment;

    public SellerOrderAdapter(int layoutResId, @Nullable List<JiuSuOrderBean.ListBean> data, JiuSuMineSellerOrderFragment fragment) {
        super(layoutResId, data);
        this.mFragment = fragment;
    }


    @Override
    protected void convert(BaseViewHolder helper, JiuSuOrderBean.ListBean item) {
        //订单编号
        helper.setText(R.id.item_tv_orderid,"订单号："+item.getOrder_sn());
        //下单时间
        helper.setText(R.id.item_tv_order_time, DateUtil.longToDate(item.getAdd_time(),null));
        //商品名称+ 数量
        helper.setText(R.id.item_tv_title,item.getGoods_name()+"*"+item.getGoods_num());
        //商品总价价格
        helper.setText(R.id.item_tv_money,item.getOrder_amount()+"元");
        //订单状态 卖家未接受
        if (item.getShipping_status()==0){
            helper.setVisible(R.id.item_tv_accept_order,true);
        }else{
            helper.setVisible(R.id.item_tv_accept_order,false);
            //卖家已接受
            helper.setText(R.id.item_tv_status, item.getOrder_state());
            helper.setTextColor(R.id.item_tv_status,getColor(item.getOrder_status()));
        }
             //接受订单
            helper.getView(R.id.item_tv_accept_order).setOnClickListener(v -> {
                //记录选中的item
                mSelectPosition=helper.getLayoutPosition();
                mFragment.confirmOrder(item.getOrder_sn());
            });
        //解决图片加载不闪烁的问题,可以在加载时候，对于已经加载过的item,  采用比对tag方式判断是否需要重新计算高度
        ImageView img_oil = helper.getView(R.id.item_img_oil);
        if(!item.getGoods_img().equals(img_oil.getTag())){
            img_oil.setTag(null);//需要清空tag，否则报错
            GlideUtils.loadImage(mContext, item.getGoods_img(), img_oil);
            img_oil.setTag(R.id.imageid,item.getGoods_img()+ helper.getLayoutPosition());
        }
    }


    /**
     * 根据状态值改变字体颜色
     * @param status 0未确认 1已确认 3 交易关闭 4 交易成功
     * @return color
     */
    private int getColor(int status) {
        switch (status) {
            case 3:
                return mContext.getResources().getColor(R.color.text_red_f04f4f);
            case 4:
                return mContext.getResources().getColor(R.color.text_green_26a69a);
            default:
                return mContext.getResources().getColor(R.color.text_orange_f7bc2e);
        }
    }


    /**
     * 刷新指定条目
     */
    public  void  notifyPosition(){
        //改为已接受
        mData.get(mSelectPosition).setShipping_status(1);
        mData.get(mSelectPosition).setOrder_status(2);
        mData.get(mSelectPosition).setOrder_state("待买家提货");
        notifyItemRangeChanged(mSelectPosition,mSelectPosition+1);
    }
}
