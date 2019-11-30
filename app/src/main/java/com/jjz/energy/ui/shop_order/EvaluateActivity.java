package com.jjz.energy.ui.shop_order;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.presenter.order.EvaluatePresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.file.FileUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.glide.MyGlideEngine;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.order.IEvaluateView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Features: 评价一下
 * @author: create by chenhao on 2019/10/26
 */
@RuntimePermissions
public class EvaluateActivity extends BaseActivity<EvaluatePresenter> implements IEvaluateView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rb_evaluate_bad)
    RadioButton rbEvaluateBad;
    @BindView(R.id.rb_evaluate_ordinary)
    RadioButton rbEvaluateOrdinary;
    @BindView(R.id.rg_evaluate)
    RadioGroup rgEvaluate;
    @BindView(R.id.rb_evaluate_good)
    RadioButton rbEvaluateGood;
    @BindView(R.id.et_evaluat)
    EditText etEvaluat;
    @BindView(R.id.img_take_photo)
    ImageView imgTakePhoto;
    @BindView(R.id.tv_put_evaluate)
    TextView tvPutEvaluate;
    /**
     * 需要上传的图片
     */
    private Uri fileUri;
    /**
     * 订单编号
     */
    private String order_sn;
    /**
     * 选中哪个 默认很棒
     */
    private int checkedIndex = 3;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("评价");
        order_sn = getIntent().getStringExtra(Constant.ORDER_SN);
        rgEvaluate.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId==R.id.rb_evaluate_bad){
                    checkedIndex = 1;
                }else if (checkedId==R.id.rb_evaluate_ordinary ){
                    checkedIndex=2;
                } else{
                    checkedIndex=3;
                }
        });
    }


    /**
     * 提交评价信息
     */
    private void putEvaluate(String file) {
        HashMap<String,String> map = new HashMap<>();
        map.put("start",checkedIndex+"");
        map.put(Constant.ORDER_SN,order_sn);
        map.put("content",etEvaluat.getText().toString()+"");
        mPresenter.putEvaluateInfo(PacketUtil.getRequestPacket(map),file);
    }

    /**
     * 上传头像
     */
    private void compressPhotos() {
        if (fileUri!=null){
        //压缩图片
        Luban.with(this)
                .load( FileUtil.getRealFilePath(mContext,fileUri ))
                .ignoreBy(100)
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        showLoading();
                    }

                    @Override
                    public void onSuccess(File file) {
                        stopLoading();
                        //开始提交
                        putEvaluate(file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("图片压缩失败", e.getMessage());
                        showToast("图片压缩失败，请重试");
                        stopLoading();
                    }
                }).launch();

        } else {
            putEvaluate("");
        }
    }


    //提交评价信息成功
    @Override
    public void isPutEvaluateInfoSuc(String data) {
        showToast("评价成功");
        finish();
    }

    @Override
    protected EvaluatePresenter getPresenter() {
        return new EvaluatePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_evaluation;
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
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }



    @OnClick({R.id.ll_toolbar_left, R.id.img_take_photo, R.id.tv_put_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //选择照片 一张
            case R.id.img_take_photo:
                EvaluateActivityPermissionsDispatcher.takePhotoWithCheck(this);
                break;
                //提交
            case R.id.tv_put_evaluate:
                if (StringUtil.isEmpty(etEvaluat.getText().toString())){
                    showToast("请填写评价内容");
                    return;
                }
                compressPhotos();
                break;
        }
    }



    // =================================================== 拍照 或选择照片


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //返回的图片集合
            List<Uri> mSelected = Matisse.obtainResult(data);
            fileUri = mSelected.get(0);
            //加载图片
            GlideUtils.loadRoundCircleImage(mContext, fileUri.toString(), imgTakePhoto);
        }

    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void takePhoto() {
        Matisse.from(this)
                .choose(EnumSet.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP))//照片视频全部显示
                .countable(true)//有序选择图片
                .maxSelectable(1)//最大9张
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题
                .imageEngine(new MyGlideEngine())//加载方式 glide
                .capture(true) //是否提供拍照功能
                .captureStrategy(new CaptureStrategy(true, Constant.MATISSE_FILE_PATH))//存储到哪里
                .forResult(Constant.REQUEST_CODE_CHOOSE);
    }

    //用户选择的不再询问后回调  非必须的方法
    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void multiNeverAsk() {
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有相机权限您就不能进行拍照哦，请您前往设置页面打开拍照和读取sd" +
                "卡的权限！", "取消", "去设置", () -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri1 = Uri.parse("package:" + getPackageName());
            intent.setData(uri1);
            startActivityForResult(intent, 20);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EvaluateActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }
}
