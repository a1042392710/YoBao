package com.jjz.energy.ui.mine.information;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.entry.enums.SexEnum;
import com.jjz.energy.presenter.mine.MineInformationPresenter;
import com.jjz.energy.ui.mine.shipping_address.AddressManagerActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.file.FileUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.glide.MyGlideEngine;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.mine.IMineInfomationView;
import com.jjz.energy.widgets.datepicker.CustomDatePicker;
import com.jjz.energy.widgets.singlepicker.SinglePicker;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.EnumSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Features: 个人信息
 * @author: create by chenhao on 2019/7/16
 */
@RuntimePermissions
public class MineInfomationActivity extends BaseActivity<MineInformationPresenter> implements IMineInfomationView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_brithday)
    TextView tvBirthday;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.ll_receipt_address)
    LinearLayout llReceiptAddress;
    @BindView(R.id.tv_password_state)
    TextView tvPasswordState;
    @BindView(R.id.ll_set_password)
    LinearLayout llSetPassword;

    /**
     * 时间选择器
     */
    private CustomDatePicker mCustomDatePicker;

    //单项选择器 (选择男女）
    private SinglePicker<String> pickerSex;
    private String[] mSexs = {"男", "女"};
    /**
     * 是否修改密码  false 设置新密码   true修改
     */
    private boolean isModify = false;


    @Override
    protected void initView() {
        tvToolbarTitle.setText("个人资料");
        initDatePicker();
        initSingerPicker();
        mPresenter.getUserInfo(PacketUtil.getRequestPacket(null));
    }


    /**
     * 写入用户个人信息
     */
    private void initInfo(UserInfo userInfo) {
        //头像
        GlideUtils.loadCircleImage(mContext, userInfo.getHead_pic(), imgHead);
        //账号
        tvPhoneNumber.setText(userInfo.getMobile());
        //昵称
        tvNickName.setText(userInfo.getNickname());
        tvDesc.setText("".equals(userInfo.getDesc()) ? "未设置简介" : userInfo.getDesc());
        //资料完整度
        progress.setProgress(userInfo.getCompletion());
        tvProgress.setText(userInfo.getCompletion() + "");
        //性别
        if (userInfo.getSex() == 1) {
            tvSex.setText("男");
        } else if (userInfo.getSex() == 2) {
            tvSex.setText("女");
        }
        //生日
        if (!StringUtil.isEmpty(userInfo.getBirthday())) {
            tvBirthday.setText(DateUtil.stampToDate(userInfo.getBirthday()));
        }
        /**
         * 是否设置了登录密码
         */
        if (userInfo.getHaspassword()==1){
            isModify = true;
            tvPasswordState.setText("去修改");
        }else{
            isModify = false;
            tvPasswordState.setText("去设置");
        }

    }

    /**
     * 初始化单项选择器
     */
    private void initSingerPicker() {
        //性别
        pickerSex = new SinglePicker<>(this, mSexs);
        pickerSex.setItemWidth(200);
        pickerSex.setTitleText("请选择");
        pickerSex.setOnItemPickListener((index, item) -> {
            //默认性别
            String defultSex = tvSex.getText().toString();
            if (index == 0) {
                //修改性别 （性别一致不修改）
                if (!defultSex.equals(SexEnum.MAN.getName())) {
                    changeSex(SexEnum.MAN.getName());
                }
            } else {
                //修改性别 （性别一致不修改）
                if (!defultSex.equals(SexEnum.WOMAN.getName())) {
                    changeSex(SexEnum.WOMAN.getName());
                }
            }
        });
    }

    /**
     * 初始化日期选择器
     */
    private void initDatePicker() {
        mCustomDatePicker = new CustomDatePicker(this, time -> {
            // 回调接口，获得选中的时间
            String birthDayDate = time.split(" ")[0];
            tvBirthday.setText(birthDayDate);
            //提交修改
            changeData("birthday", DateUtil.dateToStampStr(birthDayDate) + "", "");
        }); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        mCustomDatePicker.showSpecificTime(false); // 不显示时和分
        mCustomDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    /**
     * 设置性别
     *
     * @param sex
     */
    private void changeSex(String sex) {
        tvSex.setText(sex);
        changeData("sex", "男".equals(sex) ? "1" : "2", "");
    }

    /**
     * 提交数据
     */
    private void changeData(String key, String value, String file) {
        mPresenter.putUserInfo(PacketUtil.getRequestPacket(Utils.stringToMap(key, value)), file);
    }

    /**
     * 上传头像
     *
     * @param uri
     */
    private void updateImg(Uri uri) {
        //压缩图片
        Luban.with(this)
                .load( FileUtil.getRealFilePath(mContext,uri ))
                .ignoreBy(100)
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        Log.e("久速","开始压缩头像");
                        showLoading();
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.e("久速","压缩成功，开始上传");
                        stopLoading();
                        //开始上传头像
                        mPresenter.putUserInfo(PacketUtil.getRequestPacket(null),
                                file.getPath());

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("图片压缩失败", e.getMessage());
                        showToast("图片压缩失败，请重试");
                        stopLoading();
                    }
                }).launch();

    }

    @Override
    public void isGetInfoSuccess(UserInfo data) {
        initInfo(data);
    }

    @Override
    public void isSuccess(UserInfo data) {
        initInfo(data);
    }

    // =================================================== 拍照 或选择照片


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //返回的图片集合
            List<Uri> mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
            //加载图片
            GlideUtils.loadHead(mContext, mSelected.get(0).toString(), imgHead);
            //开始上传头像
            updateImg(mSelected.get(0));
        }
        //修改昵称
        if (requestCode == 10 && resultCode == 10) {
            tvNickName.setText(data.getStringExtra("nick_name"));
        }
        //修改简介
        if (requestCode == 20 && resultCode == 10) {
            tvDesc.setText(data.getStringExtra("desc"));
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
        MineInfomationActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    //================================================ 方法重写和生命周期



    @Override
    protected MineInformationPresenter getPresenter() {
        return new MineInformationPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_infomation;
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

    @OnClick({R.id.ll_toolbar_left, R.id.ll_head, R.id.ll_nick_name, R.id.ll_sex,
            R.id.ll_brithday, R.id.ll_receipt_address, R.id.ll_desc ,R.id.ll_set_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //头像
            case R.id.ll_head:
                MineInfomationActivityPermissionsDispatcher.takePhotoWithCheck(this);
                break;
            //昵称
            case R.id.ll_nick_name:
                startActivityForResult(new Intent(mContext, ChangeNickNameActivity.class).putExtra("nick_name", tvNickName.getText().toString()), 10);
                break;
            //简介
            case R.id.ll_desc:
                String desc = tvDesc.getText().toString();
                startActivityForResult(new Intent(mContext, ChangeDescActivity.class).putExtra(
                        "desc", desc.equals("未设置简介") ? "" : desc), 20);
                break;
            //性别
            case R.id.ll_sex:
                pickerSex.show();
                break;
            //生日
            case R.id.ll_brithday:
                String birthdayStr = tvBirthday.getText().toString();
                if (StringUtil.isEmpty(birthdayStr) || "请选择".equals(birthdayStr)) {
                    mCustomDatePicker.showNow();
                } else {
                    mCustomDatePicker.show(tvBirthday.getText().toString());
                }
                break;
            //收货地址
            case R.id.ll_receipt_address:
                startActivity(new Intent(mContext, AddressManagerActivity.class));
                break;

            //设置密码
            case R.id.ll_set_password:
                startActivity(new Intent(mContext, MineSettingPasswordActivity.class).putExtra("isMotify",isModify));
                break;
        }
    }

}
