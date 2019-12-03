package com.jjz.energy.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.CommonSelectPhotoAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.presenter.mine.FeedBackPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.file.FileUtil;
import com.jjz.energy.util.flowlayout.FlowLayout;
import com.jjz.energy.util.flowlayout.TagAdapter;
import com.jjz.energy.util.flowlayout.TagFlowLayout;
import com.jjz.energy.util.glide.MyGlideEngine;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.mine.IFeedBackView;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Features: 意见反馈
 * @author: create by chenhao on 2019/8/21
 */
@RuntimePermissions
public class FeedBackActivity extends BaseActivity <FeedBackPresenter> implements IFeedBackView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.et_feed_back_phone)
    EditText etFeedBackPhone;
    @BindView(R.id.et_feed_back_content)
    EditText etFeedBackContent;
    @BindView(R.id.rv_feed_back)
    RecyclerView rvFeedBack;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    /**
     * 意见反馈
     */
    private String[] mVals = new String[]
            {"软件问题", "物流问题", "商品问题", "退换货问题", "其他问题"};

    /**
     * 选中的反馈类型下标
     */
    private String seleteFeedPosition = "";

    /**
     * 选择图片 recyclerView的适配器
     */
    private CommonSelectPhotoAdapter mSelectPhotoAdapter;

    /**
     * 选中的所有图片
     */
    private LinkedList<Uri> mSelectPhotos;


    @Override
    protected FeedBackPresenter getPresenter() {
        return new FeedBackPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_feed_back;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("意见反馈");
        setData();
        initFlowLayout();
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
        rvFeedBack.setLayoutManager(new GridLayoutManager(mContext, 4));
        //适配器实例化 最多选三张
        mSelectPhotoAdapter = new CommonSelectPhotoAdapter(R.layout.item_put_commodity_select_photo, mSelectPhotos, 3);
        rvFeedBack.setAdapter(mSelectPhotoAdapter);
    }

    /**
     * 初始化流布局
     */
    private void initFlowLayout() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        flowlayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_feed_back_flowlayout,
                        flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        //点击事件
        flowlayout.setOnTagClickListener((view, position, parent) -> {
            //记录选中的意见下标
            seleteFeedPosition = String.valueOf(position + 1);
            return true;
        });
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
            }
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
            //去除拍照按钮，最多可选择3张图片
            maxPhoto = 4 - mSelectPhotos.size();
            //读取权限成功后 ，开启照片选择器
            FeedBackActivityPermissionsDispatcher.takePhotoWithCheck(this);
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
        FeedBackActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_submit:
                if (StringUtil.isEmpty(etFeedBackPhone.getText().toString())){
                    showToast("请填写手机号码");
                    return;
                }
                if (StringUtil.isEmpty(etFeedBackContent.getText().toString())){
                    showToast("请填写反馈内容");
                    return;
                }
                compressPhotos();
                break;
        }
    }

    // ================================ 数据提交

    /**
     * 待上传的所有图片
     */
    private List<File> mFileList;
    /**
     * 压缩图片
     */
    private void compressPhotos() {
        mFileList = new ArrayList<>();
        //无图片直接提交 -1 是减去 添加图片的那个数据
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
        Map<String, String> map = new HashMap<>();
        //反馈内容
        map.put("content", etFeedBackContent.getText().toString());
        //联系方式
        map.put("contact", etFeedBackPhone.getText().toString());
        //意见类型
        map.put("type", seleteFeedPosition);
        //提交意见反馈
        mPresenter.feedbackSubmit(PacketUtil.getRequestPacket(map),mFileList);
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
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void isSuccess(String data) {
        showToast("我们已经收到您提交的意见,祝您生活愉快");
        finish();
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }
}
