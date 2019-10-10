package com.jjz.energy.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.CommonSelectPhotoAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.GoodsClassificationBean;
import com.jjz.energy.presenter.home.PutCommodityPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.file.FileUtil;
import com.jjz.energy.util.glide.MyGlideEngine;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.home.IPutCommodityView;
import com.jjz.energy.widgets.singlepicker.SinglePicker;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Features: 发布宝贝
 * @author: create by chenhao on 2019/8/1
 */
@RuntimePermissions
public class PutCommodityActivity extends BaseActivity <PutCommodityPresenter>implements IPutCommodityView {

    /**
     * 选择分类的跳转CODE
     */
    public static final int CLASSIFICATION_INTENT_CODE = 20;

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
    @BindView(R.id.tv_point_discount_text)
    TextView tvPointDiscountText;
    @BindView(R.id.tv_point_discount)
    TextView tvPointDiscount;
    @BindView(R.id.ll_point_discount)
    LinearLayout llPointDiscount;

    /**
     * 选择图片 recyclerView的适配器
     */
    private CommonSelectPhotoAdapter mSelectPhotoAdapter;
    /**
     * 选中的所有图片
     */
    private LinkedList<Uri> mSelectPhotos;

    //单项选择器 (选择折扣）
    private SinglePicker<String> pickerPoint;
    private String[] mPointDiscounts = {"不打折", "九折", "八折", "七折", "六折", "五折", "四折", "三折", "二折", "一折"};
    /**
     * 地区
     */
    private String address;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("发布宝贝");
        tvToolbarRight.setText("发布");
        EventBus.getDefault().register(this);
        //地区
        address = SpUtil.init(mContext).readString("locationAddress");
        tvLocationAddress.setText(address.replace("/"," "));
        initSingerPicker();
        initRv();
    }

    /**
     * 初始化单项选择器
     */
    private void initSingerPicker() {
        //性别
        pickerPoint = new SinglePicker<>(this, mPointDiscounts);
        pickerPoint.setItemWidth(200);
        pickerPoint.setTitleText("请选择");
        //选中事件
        pickerPoint.setOnItemPickListener((index, item) -> {
            tvPointDiscount.setText(mPointDiscounts[index]);
        });
    }

    //初始化列表
    private void initRv() {
        //初始化数据
        mSelectPhotos = new LinkedList<>();
        mSelectPhotos.add(Uri.parse("d"));
        //设置网格布局
        rvPhoto.setLayoutManager(new GridLayoutManager(mContext, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //适配器实例化 最多选三张
        mSelectPhotoAdapter =
                new CommonSelectPhotoAdapter(R.layout.item_put_commodity_select_photo,
                        mSelectPhotos, 5);
        rvPhoto.setAdapter(mSelectPhotoAdapter);
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right, R.id.ll_commodity_type,R.id.ll_point_discount,
            R.id.ll_commodity_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //发布
            case R.id.tv_toolbar_right:
                if (isSubmitCheck()) {
                    compressPhotos();
                }
                break;
            //选择分类
            case R.id.ll_commodity_type:
                startActivityForResult(new Intent(mContext, ClassificationActivity.class),
                        CLASSIFICATION_INTENT_CODE);
                break;
            //写入金额 是否包邮等
            case R.id.ll_commodity_money:
                showMoneyPopView();
                break;
            //积分折扣
            case R.id.ll_point_discount:
                pickerPoint.show();
                break;
        }
    }

    //================================  数据提交

    /**
     * 提交前的检查
     * @return
     */
    private boolean isSubmitCheck() {
        String title = etCommodityTitle.getText().toString();
        String content = etCommodityContent.getText().toString();
        if (StringUtil.isEmpty(title) || title.length() < 5) {
            showToast("标题至少5个字哦");
            return false;
        }
        if (StringUtil.isEmpty(content) || content.length() < 10) {
            showToast("内容至少10个字哦");
            return false;
        }
        if (StringUtil.isListEmpty(mSelectPhotos) ) {
            showToast("您还没有选择宝贝图片");
            return false;
        }

        if (mClassificationBean==null){
            showToast("您还没有给宝贝选择类别");
            return false;
        }

        if (mMoneyInfo==null){
            showToast("请给宝贝开个价哦");
            return false;
        }
        return true;
    }

    /**
     * 待上传的所有图片
     */
    private List<File> mFileList;

    /**
     * 压缩图片
     */
    private void compressPhotos() {
        mFileList = new ArrayList<>();

        //无图片直接提交
        if (mSelectPhotos.size() - 1 > 0) {
            //有图片则压缩
            for (int i = 0; i < mSelectPhotos.size() - 1; i++) {
                int finalI = 1 + i;
                Luban.with(this)
                        .load(FileUtil.getRealFilePath(mContext, mSelectPhotos.get(i)))
                        .ignoreBy(100)
                        .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                showLoading();
                            }

                            @Override
                            public void onSuccess(File file) {
                                //压缩完图片后提交数据
                                mFileList.add(file);
                                if (finalI == mSelectPhotos.size() - 1) {
                                    stopLoading();
                                    submit();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                showToast("图片压缩失败，请重试");
                                stopLoading();
                            }
                        }).launch();
            }
        } else {
            submit();

        }
    }


    /**
     * 提交数据
     */
    private void submit() {
        HashMap<String, String> hashMap = new HashMap<>();
        //标题
        hashMap.put("goods_name",etCommodityTitle.getText().toString().trim());
        //内容
        hashMap.put("mobile_content",etCommodityContent.getText().toString());
        //分类id todo 暂定
        hashMap.put("cat_id","1");
        //市场价
        hashMap.put("market_price",mMoneyInfo.oldMoney);
        //本店价
        hashMap.put("shop_price",mMoneyInfo.newMoney);
        //是否全新  1 全新 0 二手
        hashMap.put("is_mnh",cbIsNewCommodity.isChecked()?"1":"0");
        //是否包邮  1 包邮 0不包邮
        hashMap.put("is_free_shipping",mMoneyInfo.isFreight?"1":"0");
        //邮费
        hashMap.put("shipping_price",mMoneyInfo.freight);
        //商家有 积分抵扣 todo 暂定
        hashMap.put("exchange_integral","6");
        //商品数量
        hashMap.put("store_count",mMoneyInfo.num+"");
        //根据逗号分隔到List数组中
        List<String> list= Arrays.asList(address.split("/"));
        if (list.size()>2){
            //地区
            hashMap.put("province",list.get(0));
            hashMap.put("city",list.get(1));
            hashMap.put("district",list.get(2));
        }
        mPresenter.putCommodity(PacketUtil.getRequestPacket(hashMap),mFileList);
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
        //商品数量
        TextView item_et_num = popView.findViewById(R.id.item_et_num);

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
                judgeNumber(s, item_et_new_moeny);
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
                judgeNumber(s, item_et_new_moeny);
            }
        });
        //是否包邮
        CheckBox item_cb_shipping = popView.findViewById(R.id.item_cb_shipping);
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
                    item_cb_shipping.setChecked(false);
                    item_tv_freight.setTextColor(getResources().getColor(R.color.text_black33));
                } else {
                    item_cb_shipping.setChecked(true);
                    item_tv_freight.setTextColor(getResources().getColor(R.color.text_black96));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //包邮则清除运费内容
        item_cb_shipping.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                item_et_freight.setText("");
            }
        });
        //如果已经存储了信息,则写入
        if (mMoneyInfo != null) {
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
            if (StringUtil.isEmpty(item_et_num.getText().toString())) {
                showToast("请输入数量");
                return;
            }
            //金钱信息
            mMoneyInfo = new MoneyInfo(item_et_new_moeny.getText().toString(),
                    item_et_old_moeny.getText().toString(), item_et_freight.getText().toString(),
                    item_cb_shipping.isChecked(),Integer.valueOf(item_et_num.getText().toString()));
            tvCommodityMoney.setText(mMoneyInfo.newMoney+"元");
            //弹窗消失时，隐藏软键盘
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(item_et_freight.getWindowToken(), 2);
            }
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
           popupWindow.dismiss();

        });

    }

    /**
     * 金额输入框中的内容限制（最大：小数点前7位，小数点后2位）
     *
     * @param edt
     */
    public void judgeNumber(Editable edt, EditText editText) {

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
                edt.delete(index - 1, index);//删除光标前的字符
                return;
            }
        }
        if (posDot > 7) {//小数点前大于5位数就删除光标前一位
            edt.delete(index - 1, index);//删除光标前的字符
            return;
        }
        if (temp.length() - posDot - 1 > 2)//如果包含小数点
        {
            edt.delete(index - 1, index);//删除光标前的字符
            return;
        }
    }

    @Override
    public void isPutCommditySuccess(String data) {
        showToast("发布成功");
        //跳转发布详情
        startActivity(new Intent(mContext, CommodityDetailActivity.class));
        finish();
    }



    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }


    class MoneyInfo {
        private String oldMoney;
        private String newMoney;
        private boolean isFreight;
        private String freight;
        private int num;

        public MoneyInfo(String newMoney, String oldMoney, String freight, boolean isFreight,int num) {
            this.oldMoney = oldMoney;
            this.newMoney = newMoney;
            this.isFreight = isFreight;
            this.freight = freight;
            this.num = num;
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
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有相机权限您就不能进行拍照哦，请您前往设置页面打开拍照权限！",
                () -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri1 = Uri.parse("package:" + getPackageName());
                    intent.setData(uri1);
                    startActivityForResult(intent, 20);
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PutCommodityActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }


    // =================================== 生命周期和方法重写
    /**
     * 记录选中的分类
     */
    private GoodsClassificationBean mClassificationBean;
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
            } else if (requestCode == CLASSIFICATION_INTENT_CODE) {
                //获取分类数据
                mClassificationBean = (GoodsClassificationBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
                tvCommodityType.setText(mClassificationBean.getGoods_type_name());
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
    protected PutCommodityPresenter getPresenter() {
        return new PutCommodityPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_put_commodity;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

}
