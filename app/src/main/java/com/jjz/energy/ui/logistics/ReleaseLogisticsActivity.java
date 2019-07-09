package com.jjz.energy.ui.logistics;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
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
//    DatePickerDialog mStartPicker;
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

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
    DateFormat format =  DateFormat.getDateTimeInstance();
    //获取日期格式器对象
    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    @OnClick({R.id.ll_toolbar_left, R.id.tv_time_start, R.id.tv_time_end, R.id.tv_location_start,
            R.id.tv_location_end, R.id.cv_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_toolbar_left:
                finish();
                break;

            //选择发货时间
            case R.id.tv_time_start:
                //生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
                new DatePickerDialog
                        (this, (view1, year, month, dayOfMonth) -> {
                    //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            tvTimeStart.setText(year+"年"+month+"月"+dayOfMonth+"日");
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

                //选择卸货时间
            case R.id.tv_time_end:
               new TimePickerDialog(this, (view12, hourOfDay
                        , minute) -> {

                        },1,10,false).show();
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
}
