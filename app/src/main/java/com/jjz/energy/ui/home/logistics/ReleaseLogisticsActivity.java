package com.jjz.energy.ui.home.logistics;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.widgets.datepicker.CustomDatePicker;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 发布物流
 * @author: create by chenhao on 2019/7/1
 */
public class ReleaseLogisticsActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_location_start)
    TextView tvLocationStart;
    @BindView(R.id.tv_location_end)
    TextView tvLocationEnd;
    @BindView(R.id.et_goods_name)
    EditText etGoodsName;
    @BindView(R.id.et_goods_money)
    EditText etGoodsMoney;
    @BindView(R.id.et_goods_weight)
    EditText etGoodsWeight;
    @BindView(R.id.et_goods_volume)
    EditText etGoodsVolume;
    @BindView(R.id.et_goods_desc)
    EditText etGoodsDesc;
    @BindView(R.id.et_sender)
    EditText etSender;
    @BindView(R.id.et_sender_phone)
    EditText etSenderPhone;
    @BindView(R.id.et_receiver)
    EditText etReceiver;
    @BindView(R.id.et_receiver_phone)
    EditText etReceiverPhone;
    @BindView(R.id.cv_release)
    CardView cvRelease;
    /**
     * 时间选择器
     */
    private CustomDatePicker mCustomDatePicker;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_release_logistics;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("发布物流");
        //初始化时间选择器
        mCustomDatePicker = new CustomDatePicker(this, time -> {
            // 回调接口，获得选中的时间
//            String birthDayDate = time.split(" ")[0];
            tvTimeStart.setText(time);
        }); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mCustomDatePicker.showSpecificTime(true); // 不显示时和分
        mCustomDatePicker.setIsLoop(false); // 不允许循环滚动

    }




    @OnClick({R.id.ll_toolbar_left, R.id.tv_time_start, R.id.tv_location_start,
            R.id.tv_location_end, R.id.cv_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_toolbar_left:
                finish();
                break;

            //选择发货时间
            case R.id.tv_time_start:
                if (StringUtil.isEmpty(tvTimeStart.getText().toString()) || "请选择".equals(tvTimeStart.getText().toString())) {
                    mCustomDatePicker.showNow();
                } else {
                    mCustomDatePicker.show(tvTimeStart.getText().toString());
                }
                break;

                //选择起点
            case R.id.tv_location_start:
                break;

                //选择终点
            case R.id.tv_location_end:
                break;

                //发布物流
            case R.id.cv_release:
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
