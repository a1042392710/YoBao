package com.jjz.energy.ui.jiusu_shop.shop_order.refund_order;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.jjz.energy.R;
import com.jjz.energy.adapter.CommonSelectPhotoAdapter;
import com.jjz.energy.adapter.RefundTypeAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.order.ApplicationRefundBean;
import com.jjz.energy.entry.order.RefundTypeBean;
import com.jjz.energy.presenter.order.ApplicationRefundPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.file.FileUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.glide.MyGlideEngine;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.order.IApplicationRefundView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
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
 * @author chenhao 2018/12/27
 * @function 申请售后
 */
@RuntimePermissions
public class ApplicationRefundActivity extends BaseActivity<ApplicationRefundPresenter> implements IApplicationRefundView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    //商品图片
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    //商品价格
    @BindView(R.id.tv_commodity_prices)
    TextView tvCommodityPrices;
    //商品介绍
    @BindView(R.id.tv_home_appliance_type)
    TextView tvHomeApplianceType;
    //商品规格
    @BindView(R.id.tv_home_appliance_size)
    TextView tvHomeApplianceSize;
    //商品 规格介绍
    @BindView(R.id.tv_number)
    TextView tvNumber;
    //商品列表
    @BindView(R.id.rl_commodity)
    RelativeLayout rlCommodity;
    //选择退款理由
    @BindView(R.id.tv_select_refund_cause)
    TextView tvSelectRefundCause;
    @BindView(R.id.tv_select_refund_cause_text)
    TextView tvSelectRefundCauseText;
    //退款金额
    @BindView(R.id.et_refund_money)
    EditText etRefundMoney;
    //退款理由
    @BindView(R.id.et_refund_reson)
    EditText etRefundReson;
    //图片列表
    @BindView(R.id.rv_refund_photo)
    RecyclerView rvRefundPhoto;
    //提交退款申请
    @BindView(R.id.tv_refund_submit)
    TextView tvRefundSubmit;
    @BindView(R.id.tv_refund_reson_text)
    TextView tvRefundResonText;

    /**
     * 选择图片 recyclerView的适配器
     */
    private CommonSelectPhotoAdapter mSelectPhotoAdapter;
    /**
     * 选中的所有图片
     */
    private LinkedList<Uri> mSelectPhotos;
    /**
     * 商品id
     */
    private int rec_id;
    /**
     * 退款类型  0 仅退款  1 退货退款
     */
    private int type ;


    @Override
    protected void initView() {
        rec_id = getIntent().getIntExtra(Constant.REC_ID,0);
        type = getIntent().getIntExtra("type",0);
        if (Constant.RETURN_SALES == type){
            tvToolbarTitle.setText("申请退货退款");
            tvRefundResonText.setText("退货退款说明");
            tvSelectRefundCauseText.setText("退货退款原因");
        }else{
            tvToolbarTitle.setText("申请退款");
        }
        //初始化
        setData();
        //获取退款的商品信息
        mPresenter.getApplicationData(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.REC_ID, rec_id+"")) );
    }

    /**
     * 初始化
     */
    private void setData() {
        //注册EventBus
        EventBus.getDefault().register(this);
        //初始化数据
        mSelectPhotos = new LinkedList<>();
        mSelectPhotos.add(Uri.parse("d"));
        //设置网格布局
        rvRefundPhoto.setLayoutManager(new GridLayoutManager(mContext, 4));
        //适配器实例化 最多选5张
        mSelectPhotoAdapter = new CommonSelectPhotoAdapter(R.layout.item_put_commodity_select_photo, mSelectPhotos, 5);
        rvRefundPhoto.setAdapter(mSelectPhotoAdapter);
        mResonAdapter = new RefundTypeAdapter(R.layout.item_refund_type,new ArrayList<>());
    }

    // ==================================== 退款理由

    /**
     * 退款原因/退货方式数据源
     */
    private List<RefundTypeBean> mResonList;
    /**
     * 退款原因/退货方式
     */
    private RefundTypeAdapter mResonAdapter;

    /**
     * 退货方式
     */
    private void refundTypeClick(String title) {
        View popView = View.inflate(mContext, R.layout.item_pop_refund_type, null);
        RecyclerView rv = popView.findViewById(R.id.rv_refund_type);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //退款原因
        mResonAdapter = new RefundTypeAdapter(R.layout.item_refund_type, mResonList);
        rv.setAdapter(mResonAdapter);
        ((TextView) popView.findViewById(R.id.item_tv_title)).setText(title);
        PopupWindow chargePopWindow = PopWindowUtil.getInstance().showBottomWindow(mContext, popView);
        popView.findViewById(R.id.pop_card_close).setOnClickListener(v -> {
            tvSelectRefundCause.setText(mResonAdapter.getSelectStr());
            chargePopWindow.dismiss();
        });
    }

    /**
     * 存下数据源
     */
    private ApplicationRefundBean mRefundBean;

    @Override
    public void isSuccess(ApplicationRefundBean data) {
        mRefundBean = data;
        //商品图片
        GlideUtils.loadRoundCircleImage(this, data.getOrder_goods().getGoods_images(), imgCommodity);
        //商品名称
        tvHomeApplianceType.setText(data.getOrder_goods().getGoods_name());
        //商品价格
        tvCommodityPrices.setText("¥"+data.getOrder_goods().getFinal_price());
        //商品规格
        tvHomeApplianceSize.setText(data.getOrder_goods().getSpec_key_name());
        //商品数量
        tvNumber.setText("x"+data.getOrder_goods().getGoods_num());
        //退款原因
        mResonList = data.getReason();
        //修改申请
        if (data.getReturn_goods() != null&&!"0".equals(data.getReturn_goods().getRefund_money())) {
            //退款总价
            etRefundMoney.setText(data.getReturn_goods().getRefund_money());
            for (RefundTypeBean refundTypeBean : mResonList) {
                if (refundTypeBean.getId() == data.getReturn_goods().getReason()) {
                    refundTypeBean.setSelect(true);
                    tvSelectRefundCause.setText(refundTypeBean.getValue());
                    break;
                }
            }
            //退款说明
            etRefundReson.setText(data.getReturn_goods().getDescribe());
            String[] sourceStrArray = data.getReturn_goods().getImgs().split(",");
            //下载图片
            downLoadImg(sourceStrArray);
        } else {
            //正常申请 退款总价
            etRefundMoney.setText(String.valueOf(data.getOrder_goods().getTotal()));
            if (!StringUtil.isListEmpty(mResonList)) {
                mResonList.get(0).setSelect(true);
            }
            stopProgressDialog();
        }
    }

    /**
     * 下载图片后保存本地
     *
     * @param sourceStrArray
     */
    private void downLoadImg(String[] sourceStrArray) {
        final String[] strings = sourceStrArray;
        final Context context = getApplicationContext();
        new Thread(() -> {
            try {
                for (int i = 0; i < strings.length; i++) {
                    String url = strings[i];
                    FutureTarget<File> target = Glide.with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    mSelectPhotos.addFirst(Uri.parse(imageFile.getPath()));
                    if (i == strings.length - 1) {
                        //回主线程刷新数据
                        runOnUiThread(() -> {
                            stopProgressDialog();
                            mSelectPhotoAdapter.notifyDataSetChanged();
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                stopProgressDialog();
            }
        }).start();
    }

    @Override
    public void isSubmitSuccess(ApplicationRefundBean data) {
        //提交售后成功
        showToast("您的退款申请已经提交！");
        finish();
    }

    // ============================================ 数据提交

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
    private void submit(){
        //提交
        HashMap<String, String> map = new HashMap<>();
        map.put(Constant.REC_ID,rec_id+"");
        //退款原因
        map.put("reason", mResonAdapter.getSelectId() + "");
        //退款类型
        map.put("type", type+"");
        //退款说明
        map.put("describe", etRefundReson.getText().toString());
        //退款金额
        map.put("refund_money", etRefundMoney.getText().toString().trim());
        mPresenter.submitApplication(PacketUtil.getRequestPacket(map),mFileList);
    }

    //-------------------------------------------- 子类实现

    @Override
    protected ApplicationRefundPresenter getPresenter() {
        return new ApplicationRefundPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_application_refund;
    }

    @Override
    public void isFail(String msg) {showToast(msg);}

    @Override
    public void showLoading() {startProgressDialog();}

    @Override
    public void stopLoading() {stopProgressDialog();}


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

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
            }
        }
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_refund_submit, R.id.tv_select_refund_cause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_refund_submit:
                if (tvSelectRefundCause.getText().toString().equals("请选择")) {
                    showToast("请选择退款原因");
                    return;
                }
                if (Double.valueOf(etRefundMoney.getText().toString())<=0){
                    showToast("退款金额不能小于等于0");
                    return;
                }
                if (mRefundBean==null){
                    showToast("数据异常");
                    return;
                }

                //订单总价
                double orderPrice = mRefundBean.getOrder_goods().getTotal();
                double refundPrice = Double.valueOf(etRefundMoney.getText().toString());
                if (refundPrice>orderPrice){
                    showToast("退款金额不能大于商品总价");
                    return;
                }
                compressPhotos();
                break;
            //选择退款原因
            case R.id.tv_select_refund_cause:
                //收回软键盘
                disMissSoftKeyboard();
                refundTypeClick("退款原因");
                break;

        }
    }

    //===================================== 权限相关

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
            //去除拍照按钮，最多可选择5张图片
            maxPhoto = 6 - mSelectPhotos.size();
            //读取权限成功后 ，开启照片选择器
            ApplicationRefundActivityPermissionsDispatcher.takePhotoWithCheck(this);
        }
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void takePhoto() {
        Matisse.from(this)
                .choose(EnumSet.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP))//照片视频全部显示
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
    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
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
        ApplicationRefundActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


}
