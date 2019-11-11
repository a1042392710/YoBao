package com.jjz.energy.ui.jiusu_shop.shop_order.refund_order;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.CommonSelectPhotoAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.presenter.order.RefundPresenter;
import com.jjz.energy.ui.jiusu_shop.shop_order.ExpressCompanyActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.file.FileUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.glide.MyGlideEngine;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.order.IRefundView;
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
 * @Features: 填写退货物流
 * @author: create by chenhao on 2019/11/4
 */
@RuntimePermissions
public class ReturnLogisticsActivity extends BaseActivity <RefundPresenter> implements IRefundView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_commodity_title)
    TextView tvCommodityTitle;
    @BindView(R.id.tv_text_one)
    TextView tvTextOne;
    @BindView(R.id.tv_text_two)
    TextView tvTextTwo;
    @BindView(R.id.view_line_two)
    View viewLineTwo;
    @BindView(R.id.tv_text_three)
    TextView tvTextThree;
    @BindView(R.id.et_express_number)
    EditText etExpressNumber;
    @BindView(R.id.view_line_three)
    View viewLineThree;
    @BindView(R.id.tv_text_four)
    TextView tvTextFour;
    @BindView(R.id.tv_express_company)
    TextView tvExpressCompany;
    @BindView(R.id.rv_select_photo)
    RecyclerView rvSelectPhoto;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    /**
     * 选择图片 recyclerView的适配器
     */
    private CommonSelectPhotoAdapter mSelectPhotoAdapter;
    /**
     * 选中的所有图片
     */
    private LinkedList<Uri> mSelectPhotos;
    /*
     * 存下物流公司的信息
     */
    private ExpressCompanyBean mExpressCompanyBean ;
    /**
     * 售后id
     */
    private String return_id;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("填写退货物流");
        GlideUtils.loadRoundCircleImage(mContext,getIntent().getStringExtra("img"),imgCommodity);
        tvCommodityTitle.setText(getIntent().getStringExtra("name"));
        return_id = getIntent().getStringExtra(Constant.RETURN_ID);
        setData();
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
        rvSelectPhoto.setLayoutManager(new GridLayoutManager(mContext, 4));
        //适配器实例化 最多选三张
        mSelectPhotoAdapter = new CommonSelectPhotoAdapter(R.layout.item_put_commodity_select_photo, mSelectPhotos, 3);
        rvSelectPhoto.setAdapter(mSelectPhotoAdapter);
    }

    //买家提交退货信息成功
    @Override
    public void isBuyerPutExpressInfoSuccess(String data) {
        showToast("提交成功");
        finish();
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_express_company, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //选择物流公司
            case R.id.tv_express_company:
                startActivityForResult(new Intent(mContext, ExpressCompanyActivity.class), Constant.SELECT_COMPANY_CODE);
                break;
            //提交
            case R.id.tv_sure:
                if (StringUtil.isEmpty(etExpressNumber.getText().toString())){
                    showToast("请填写物流单号");
                    return;
                }
                if (mExpressCompanyBean==null){
                    showToast("请选择物流公司");
                    return;
                }
                compressPhotos();
                break;
        }
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
        map.put("id",return_id);
        // 2 已退货
        map.put("status",2+"");
        map.put("shipping_id",mExpressCompanyBean.getId());
        map.put("courier_number",etExpressNumber.getText().toString());
        mPresenter.buyerPutExpressInfo(PacketUtil.getRequestPacket(map),mFileList);
    }



    @Override
    protected RefundPresenter getPresenter() {
        return new RefundPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_return_logistics;
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
            }else if (requestCode == Constant.SELECT_COMPANY_CODE){
                //写入物流公司
                mExpressCompanyBean = (ExpressCompanyBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
                tvExpressCompany.setText(mExpressCompanyBean.getName());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
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
            //去除拍照按钮，最多可选择3张图片
            maxPhoto = 4 - mSelectPhotos.size();
            //读取权限成功后 ，开启照片选择器
            ReturnLogisticsActivityPermissionsDispatcher.takePhotoWithCheck(this);
        }
    }


    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
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
       ReturnLogisticsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }

}
