package com.jjz.energy.ui.home.logistics;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.ui.city.CityPickerActivity;
import com.jjz.energy.widgets.singlepicker.SinglePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 物流中心
 * @author: create by chenhao on 2019/6/28
 */
public class LogisticsActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_nearby)
    RadioButton rbNearby;
    @BindView(R.id.rg_goods)
    RadioGroup rgGoods;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.img_sort)
    ImageView imgSort;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;

    //单项选择器 (选择折扣）
    private SinglePicker<String> pickerPoint;
    private String[] mPicks = {"24内","三天内","一周内"};

    /**
     *  城市类型  0 起点  1 终点
     */
    private int cityType = 0;


    @Override
    protected void initView() {
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        LogisticsAdapter mAdapter = new LogisticsAdapter(R.layout.item_logistics,list);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext,LogisticsDetailActivity.class));
        });
        rvGoods.setAdapter(mAdapter);
        //初始化单项选择
        initSingerPicker();

    }

    /**
     * 初始化单项选择器
     */
    private void initSingerPicker() {
        //折扣
        pickerPoint = new SinglePicker<>(this, mPicks);
        pickerPoint.setItemWidth(200);
        pickerPoint.setTitleText("请选择");
        //选中事件
        pickerPoint.setOnItemPickListener((index, item) -> {
            tvTime.setText(mPicks[index]);
        });
    }



    @OnClick({R.id.img_back, R.id.tv_first, R.id.img_sort, R.id.tv_end, R.id.tv_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
                //起点
            case R.id.tv_first:
                cityType = 0;
                startActivityForResult(new Intent(mContext, CityPickerActivity.class),
                        Constant.SELECT_CITY_CODE);
                break;
                //起点终点翻转
            case R.id.img_sort:
                break;
                //终点
            case R.id.tv_end:
                cityType = 1;
                startActivityForResult(new Intent(mContext, CityPickerActivity.class).putExtra("type",1),
                        Constant.SELECT_CITY_CODE);
                break;
                //时间
            case R.id.tv_time:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择城市
        if (requestCode==Constant.SELECT_CITY_CODE&&resultCode == RESULT_OK){
            String cityName = data.getStringExtra("city");
            if (cityType==0){
                tvFirst.setText(cityName);
            }else{
                tvEnd.setText(cityName);
            }
        }
    }

    /**
     * 物流列表
     */
    class LogisticsAdapter extends BaseRecycleNewAdapter<String>{

        public LogisticsAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

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


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_logistics;
    }


}
