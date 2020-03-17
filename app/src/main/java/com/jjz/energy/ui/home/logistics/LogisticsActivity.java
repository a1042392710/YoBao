package com.jjz.energy.ui.home.logistics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.LogisticsAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.home.LogisticsListBean;
import com.jjz.energy.presenter.home.logistics.LogisticsPresenter;
import com.jjz.energy.ui.city.CityPickerActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.home.ILogisticsView;
import com.jjz.energy.widgets.singlepicker.SinglePicker;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 物流中心
 * @author: create by chenhao on 2019/6/28
 */
public class LogisticsActivity extends BaseActivity <LogisticsPresenter>implements ILogisticsView , OnRefreshLoadMoreListener {
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
    @BindView(R.id.ll_loaction_select)
    LinearLayout llLoactionSelect;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    //单项选择器 (选择折扣）
    private SinglePicker<String> pickerPoint;
    private String[] mPicks = {"24小时","三天内","一周内"};
    /**
     * 选择的时间区间 默认1天
     */
    private int mPicksDays = 1;
    /**
     * 货源类型  0 全国  1 附近
     */
    private int mLocationType = 0;

    /**
     *  城市类型  0 起点  1 终点
     */
    private int cityType = 0;
    /**
     * 页码
     */
    private int mPage = 1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;
    /**
     * 列表适配器
     */
    private LogisticsAdapter mAdapter ;

    @Override
    protected void initView() {
        //初始化单项选择
        initSingerPicker();
        initRb();
        smartRefresh.setOnRefreshLoadMoreListener(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new LogisticsAdapter(R.layout.item_logistics,new ArrayList<>());
        rvGoods.setAdapter(mAdapter);
        //进入详情
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
        startActivity(new Intent(mContext,LogisticsDetailActivity.class).putExtra("id",mAdapter.getData().get(position).getId()));
        });
        getData(false);
    }

    /**
     * 初始化选择按钮  0 全国货源 1 附近货源
     */
    private void initRb() {

        rgGoods.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId==R.id.rb_all){
                mLocationType = 0;
                llLoactionSelect.setVisibility(View.VISIBLE);
            }else{
                mLocationType = 1;
                llLoactionSelect.setVisibility(View.GONE);
            }
            getData(false);
        });
    }

    /**
     * 初始化单项选择器
     */
    private void initSingerPicker() {
        //选择时间
        pickerPoint = new SinglePicker<>(this, mPicks);
        pickerPoint.setItemWidth(200);
        pickerPoint.setTitleText("请选择");
        //选中事件
        pickerPoint.setOnItemPickListener((index, item) -> {
            if (index==0){
                mPicksDays = 1;
            }else if (index ==1){
                mPicksDays = 3;
            }else if (index ==2){
                mPicksDays = 7;
            }
            tvTime.setText(mPicks[index]);
        });
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore) {
        if (!isLoadMore){
            mPage=1;
        }
        this.isLoadMore = isLoadMore;
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        if (mLocationType==0) {
            if (!"发货地".equals(tvFirst.getText().toString())) {
                map.put("start_city", tvFirst.getText().toString()+"市");
            }
            if (!"目的地".equals(tvEnd.getText().toString())) {
                map.put("end_city", tvEnd.getText().toString()+"市");
            }
        }else {
            //根据逗号分隔到List数组中  上传用户当前定位
            String address = SpUtil.init(mContext).readString(Constant.LOCATION_ADDRESS);
            List<String> list= Arrays.asList(address.split("/"));
            if (list.size()>2){
                //地区
                map.put("city",list.get(1));
            }
        }
        if (!"装货时间".equals(tvTime.getText().toString())) {
            map.put("time", mPicksDays+"");
        }
        mPresenter.getLogisticsInfo(PacketUtil.getRequestPacket(map), isLoadMore);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        getData(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage=1;
        getData(false);
    }


    @Override
    public void isGetLogisticsInfoSuc(LogisticsListBean data) {
        if (!StringUtil.isListEmpty(data.getList()) && data.getList().size()>8){
            smartRefresh.setEnableLoadMore(true);     //有够长的数据就开启加载更多
        }else{
            smartRefresh.setEnableLoadMore(false);     //否则关闭
        }
        //加载更多
        if (isLoadMore) {
            mAdapter.addNewData(data.getList());
        } else {
            //刷新数据，无数据显示空页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "暂无数据", true, v -> {
                    getData(true);
                }));
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isNetAndServiceError) {
            //网络错误时刷新页码
            mPage=1;
            mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_timeout, "网络发生错误",true, v -> {
                getData(false);
            }));
        }
        //请求失败时页码归位
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(smartRefresh);
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
                startActivityForResult(new Intent(mContext, CityPickerActivity.class).putExtra("type",1),
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
                pickerPoint.show();
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
                getData(false);
            }else{
                tvEnd.setText(cityName);
                getData(false);
            }
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
    protected LogisticsPresenter getPresenter() {
        return new LogisticsPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_logistics;
    }

}
