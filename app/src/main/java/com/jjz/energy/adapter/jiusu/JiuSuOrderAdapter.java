package com.jjz.energy.adapter.jiusu;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;
import com.jjz.energy.ui.home.jiusu.JiuSuBillApplicationActivity;
import com.jjz.energy.ui.home.jiusu.JiuSuLookBillActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.List;

/**
 * @Features: 我的订单
 * @author: create by chenhao on 2019/4/15
 */
public class JiuSuOrderAdapter extends BaseRecycleNewAdapter<JiuSuOrderBean.ListBean> {
    public JiuSuOrderAdapter(int layoutResId, @Nullable List<JiuSuOrderBean.ListBean> data) {
        super(layoutResId, data);
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
        //订单状态
        helper.setText(R.id.item_tv_status,item.getOrder_state());
        helper.setTextColor(R.id.item_tv_status,getColor(item.getOrder_status()));
        //解决图片加载不闪烁的问题,可以在加载时候，对于已经加载过的item,  采用比对tag方式判断是否需要重新计算高度
        ImageView img_oil = helper.getView(R.id.item_img_oil);
        if(!item.getGoods_img().equals(img_oil.getTag())){
            img_oil.setTag(null);//需要清空tag，否则报错
            GlideUtils.loadImage(mContext, item.getGoods_img(), img_oil);
            img_oil.setTag(R.id.imageid,item.getGoods_img()+ helper.getLayoutPosition());
        }
        if (item.getOrder_status()==4){
            helper.getView(R.id.tv_check_bill).setVisibility(View.VISIBLE);
            setBillContent(helper.getView(R.id.tv_check_bill),item.getInvoice_status(),item.getOrder_id(),item.getOrder_amount());
        }else{
            helper.getView(R.id.tv_check_bill).setVisibility(View.GONE);
        }

    }
    //发票信息
    private void setBillContent (TextView tv, int status, String order_id , String money ){
        switch (status){
            //未开
            case  -1:
                tv.setText("申请开票");
                tv.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, JiuSuBillApplicationActivity.class).putExtra("order_id",order_id)
                    .putExtra("money",money));
                });
                break;
                //在开
            case  0:
                tv.setText("开票中");
                break;
                //已开
            case  1:
                tv.setText("查看发票");
                tv.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, JiuSuLookBillActivity.class).putExtra("order_id",order_id));
                });
                break;
                //作废
            case  2:
                tv.setVisibility(View.GONE);
                break;

        }
    }

    /**
     * 根据状态值改变字体颜色
     * @param status 0待支付 1 待卖家确认 2 待提货 3 交易关闭 4 交易成功
     * @return color
     */
    private int getColor(int status) {
        switch (status) {
            case 0:
            case 1:
            case 2:
                return mContext.getResources().getColor(R.color.text_orange_f7bc2e);
            case 3:
                return mContext.getResources().getColor(R.color.text_red_f04f4f);
            case 4:
                return mContext.getResources().getColor(R.color.text_green_26a69a);
            default:
                return mContext.getResources().getColor(R.color.text_orange_f7bc2e);
        }
    }

}
