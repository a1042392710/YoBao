package com.jjz.energy.ui.home;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.CommonSelectPhotoAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.ui.mine.ClassificationActivity;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.MyGlideEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 发布宝贝
 * @author: create by chenhao on 2019/8/1
 */
@RuntimePermissions
public class PutCommodityActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.et_commodity_title)
    EditText etCommodityTitle;
    @BindView(R.id.et_commodity_content)
    EditText etCommodityContent;
    @BindView(R.id.tv_location_address)
    TextView tvLocationAddress;
    @BindView(R.id.cb_is_new_commodity)
    CheckBox cbIsNewCommodity;
    @BindView(R.id.tv_commodity_type)
    TextView tvCommodityType;
    @BindView(R.id.tv_commodity_money)
    TextView tvCommodityMoney;

    /**
     * 选择分类的跳转CODE
     */
    public static  final int CLASSIFICATION_INTENT_CODE = 20;

    /**
     * 选择图片 recyclerView的适配器
     */
    private CommonSelectPhotoAdapter mSelectPhotoAdapter;
    /**
     * 选中的所有图片
     */
    private LinkedList<Uri> mSelectPhotos;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_put_commodity;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("发布宝贝");
        tvToolbarRight.setText("发布");
        EventBus.getDefault().register(this);
        initRv();
    }

    //初始化列表
    private void initRv() {
        //初始化数据
        mSelectPhotos = new LinkedList<>();
        mSelectPhotos.add(Uri.parse("d"));
        //设置网格布局
        rvPhoto.setLayoutManager(new GridLayoutManager(mContext, 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //适配器实例化 最多选三张
        mSelectPhotoAdapter = new CommonSelectPhotoAdapter(R.layout.item_put_commodity_select_photo, mSelectPhotos, 5);
        rvPhoto.setAdapter(mSelectPhotoAdapter);
    }




    // =================================== 生命周期和方法重写

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_CHOOSE) {
                //获取选择的图片数据
                List<Uri> mSelectPhotoTemps = Matisse.obtainResult(data);
                //将选中的所有图片都放入图片数据源
                for (Uri selectPhotoTemp : mSelectPhotoTemps) {
                    mSelectPhotos.addFirst(selectPhotoTemp);
                }
                //刷新数据
                mSelectPhotoAdapter.notifyDataSetChanged();
            } else if (requestCode == CLASSIFICATION_INTENT_CODE ) {
                //获取分类数据
                String classification_title = data.getStringExtra("classification_title");
                tvCommodityType.setText(classification_title);
            }


        }
    }


    // 方法重写
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right, R.id.tv_commodity_type,
            R.id.tv_commodity_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //发布
            case R.id.tv_toolbar_right:
                break;
                //选择分类
            case R.id.tv_commodity_type:
                startActivityForResult(new Intent(mContext, ClassificationActivity.class),CLASSIFICATION_INTENT_CODE);
                break;
                //写入金额 是否包邮等
            case R.id.tv_commodity_money:
                showMoneyPopView();
              break;
        }
    }

    // ===================================================== 价格和运费弹窗

    //存储金额信息
    private MoneyInfo mMoneyInfo;
    /**
     * 输入金额/邮费等
     */
    private void showMoneyPopView() {
        View popView = View.inflate(mContext, R.layout.item_input_money, null);
        //出手价
        EditText item_et_new_moeny = popView.findViewById(R.id.item_et_new_money);
        TextView item_tv_new_moeny = popView.findViewById(R.id.item_tv_new_money);
        item_et_new_moeny.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtil.isEmpty(s.toString())) {
                    item_tv_new_moeny.setTextColor(getResources().getColor(R.color.text_black33));
                } else {
                    item_tv_new_moeny.setTextColor(getResources().getColor(R.color.text_black96));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeNumber(s,item_et_new_moeny);
            }
        });

        //原价
        EditText item_et_old_moeny = popView.findViewById(R.id.item_et_old_moeny);
        TextView item_tv_old_money = popView.findViewById(R.id.item_tv_old_money);
        item_et_old_moeny.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtil.isEmpty(s.toString())) {
                    item_tv_old_money.setTextColor(getResources().getColor(R.color.text_black33));
                } else {
                    item_tv_old_money.setTextColor(getResources().getColor(R.color.text_black96));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeNumber(s,item_et_new_moeny);
            }
        });

        //运费
        EditText item_et_freight = popView.findViewById(R.id.item_et_freight);
        TextView item_tv_freight = popView.findViewById(R.id.item_tv_freight);
        item_et_freight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtil.isEmpty(s.toString())) {
                    item_tv_freight.setTextColor(getResources().getColor(R.color.text_black33));
                } else {
                    item_tv_freight.setTextColor(getResources().getColor(R.color.text_black96));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //是否包邮
        CheckBox item_cb_shipping = popView.findViewById(R.id.item_cb_shipping);
        //包邮则清除运费内容
        item_cb_shipping.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                item_et_freight.setText("");
            }
        });
        //如果已经存储了信息,则写入
        if (mMoneyInfo!=null){
            item_et_new_moeny.setText(mMoneyInfo.newMoney);
            item_et_old_moeny.setText(mMoneyInfo.oldMoney);
            item_et_freight.setText(mMoneyInfo.freight);
            item_cb_shipping.setChecked(mMoneyInfo.isFreight);
        }

        PopupWindow popupWindow = PopWindowUtil.getInstance().showBottomWindow(mContext, popView);
//        //确认
        popView.findViewById(R.id.item_tv_sure);
        popView.setOnClickListener(v -> {
            //保存信息
            if (StringUtil.isEmpty(item_et_new_moeny.getText().toString())) {
                showToast("请输入价格");
                return;
            }
            if (StringUtil.isEmpty(item_et_old_moeny.getText().toString())) {
                showToast("请输入原价");
                return;
            }
            mMoneyInfo = new MoneyInfo(item_et_new_moeny.getText().toString(),
                    item_et_old_moeny.getText().toString(), item_et_freight.getText().toString(),
                    item_cb_shipping.isChecked());
            tvCommodityMoney.setText(mMoneyInfo.newMoney);
            popupWindow.dismiss();
        });
    }

    /**
     * 金额输入框中的内容限制（最大：小数点前7位，小数点后2位）
     *
     * @param edt
     */
    public  void judgeNumber(Editable edt,EditText editText) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        int index = editText.getSelectionStart();//获取光标位置
        //  if (posDot == 0) {//必须先输入数字后才能输入小数点
        //  edt.delete(0, temp.length());//删除所有字符
        //  return;
        //  }
        if (posDot < 0) {//不包含小数点
            if (temp.length() <= 7) {
                return;//小于五位数直接返回
            } else {
                edt.delete(index-1, index);//删除光标前的字符
                return;
            }
        }
        if (posDot > 7) {//小数点前大于5位数就删除光标前一位
            edt.delete(index-1, index);//删除光标前的字符
            return;
        }
        if (temp.length() - posDot - 1 > 2)//如果包含小数点
        {
            edt.delete(index-1, index);//删除光标前的字符
            return;
        }
    }

    class MoneyInfo{
      private  String oldMoney ;
        private    String newMoney;
        private    boolean isFreight;
        private   String freight;

        public MoneyInfo(String newMoney,String oldMoney, String freight ,boolean isFreight) {
            this.oldMoney = oldMoney;
            this.newMoney = newMoney;
            this.isFreight = isFreight;
            this.freight = freight;
        }
    }

    // ================================================  权限相关

    /**
     * 最大可选择图片数量 默认最多5张
     */
    private int maxPhoto;

    /**
     * 申请拍照权限
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String str) {
        if (str.equals("selectPhoto")) {
            //去除拍照按钮，最多可选择6张图片
            maxPhoto = 7 - mSelectPhotos.size();
            //读取权限成功后 ，开启照片选择器
            PutCommodityActivityPermissionsDispatcher.takePhotoWithCheck(this);
        }
    }


    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void takePhoto() {
        Matisse.from(this)
                .choose(MimeType.ofImage())//照片视频全部显示
                .countable(true)//有序选择图片
                .maxSelectable(maxPhoto)//最大5张
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题R.style.Matisse_Dracula
                .imageEngine(new MyGlideEngine())//加载方式 glide
                .capture(true) //是否提供拍照功能
                .captureStrategy(new CaptureStrategy(true, Constant.MATISSE_FILE_PATH))//存储到哪里
                .forResult(Constant.REQUEST_CODE_CHOOSE);
    }

    //用户选择的不再询问后回调  非必须的方法
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void multiNeverAsk() {
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有相机权限您就不能进行拍照哦，请您前往设置页面打开拍照权限！", () -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri1 = Uri.parse("package:" + getPackageName());
                    intent.setData(uri1);
                    startActivityForResult(intent, 20);
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PutCommodityActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
