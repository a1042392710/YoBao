package com.jjz.energy.ui.mine.shop_order.refund_order;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.CommonSelectPhotoAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.util.glide.MyGlideEngine;
import com.jjz.energy.util.system.PopWindowUtil;
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
 * @Features: 卖家拒绝退货申请
 * @author: create by chenhao on 2019/11/4
 */
@RuntimePermissions
public class SellerRefuseApplicationActivity extends BaseActivity {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_refuse)
    EditText etRefuse;
    @BindView(R.id.rv_select_photo)
    RecyclerView rvSelectPhoto;
    @BindView(R.id.tv_refuse_submit)
    TextView tvRefuseSubmit;

    /**
     * 选择图片 recyclerView的适配器
     */
    private CommonSelectPhotoAdapter mSelectPhotoAdapter;

    /**
     * 选中的所有图片
     */
    private LinkedList<Uri> mSelectPhotos;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("拒绝申请");
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
        return R.layout.act_seller_refuse_application;
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_refuse_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //拒绝申请
            case R.id.tv_refuse_submit:
                break;
        }
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
            SellerRefuseApplicationActivityPermissionsDispatcher.takePhotoWithCheck(this);
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
        SellerRefuseApplicationActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


}
