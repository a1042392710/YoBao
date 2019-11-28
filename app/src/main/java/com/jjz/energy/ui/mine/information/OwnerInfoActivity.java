package com.jjz.energy.ui.mine.information;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.entry.jiusu.BindOwnerInfoBean;
import com.jjz.energy.presenter.mine.BindOwnerInfoPresenter;
import com.jjz.energy.util.IDCardValidate;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.widgets.idcardcamera.camera.IDCardCamera;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.KeyboardUtil;
import com.jjz.energy.view.mine.IBindOwnerInfoView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Features: 车主信息
 * @author: create by chenhao on 2019/4/11
 */
public class OwnerInfoActivity extends BaseActivity<BindOwnerInfoPresenter> implements IBindOwnerInfoView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id_card)
    EditText etIdCard;
    @BindView(R.id.et_license_plate)
    EditText etLicensePlate;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.img_idcard_front)
    ImageView imgIdcardFront;
    @BindView(R.id.img_idcard_reverse)
    ImageView imgIdcardReverse;

    private KeyboardUtil keyboardUtil;
    /**
     * 上传的图片类型  0 身份证正面  1 身份证反面
     */
    private int img_icard_type = 0;
    /**
     * 身份证正面
     */
    private String idcardFrontPath;
    /**
     * 身份证反面
     */
    private String idcardReversePath;

    private List<SubmitIdCardBean> mFileList;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        tvToolbarTitle.setText("实名认证");
        //键盘触摸事件
        etLicensePlate.setOnTouchListener((view, event) -> {
            if (keyboardUtil == null) {
                keyboardUtil = new KeyboardUtil(this, etLicensePlate);
                keyboardUtil.hideSoftInputMethod();
                keyboardUtil.showKeyboard();
            } else {
                keyboardUtil.showKeyboard();
            }
            return false;
        });
        //查询
        mPresenter.getBindInfo(PacketUtil.getRequestPacket(null));
    }

    /**
     * 存下用户信息
     */
    private BindOwnerInfoBean mData;

    @Override
    public void isGetSuccess(BindOwnerInfoBean data) {
        mData = data;
        //写入车主信息
        etIdCard.setText(data.getUser_idcard());
        etName.setText(data.getUser_name());
        etLicensePlate.setText(data.getLicense_plate());
        if (!StringUtil.isEmpty(data.getIdcard_front())){
            GlideUtils.loadImage(mContext,data.getIdcard_front(),imgIdcardFront);
        }
        if (!StringUtil.isEmpty(data.getIdcard_back())){
            GlideUtils.loadImage(mContext,data.getIdcard_back(),imgIdcardReverse);
        }

    }

    @Override
    public void isPutSuccess(String data) {

        UserInfo loginBean = UserLoginBiz.getInstance(mContext).readUserInfo();
        loginBean.setIs_set_idcard(1);
        UserLoginBiz.getInstance(mContext).saveUserInfo(loginBean);
        showToast("认证成功");
        finish();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_bind, R.id.img_idcard_front, R.id.img_idcard_reverse, R.id.ll_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.ll_all:
                if (keyboardUtil != null && keyboardUtil.isShow()) {
                    keyboardUtil.hideKeyboard();
                }
                break;
                //身份证正面
            case R.id.img_idcard_front:
                img_icard_type = 0;
                IDCardCamera.create(OwnerInfoActivity.this).openCamera(IDCardCamera.TYPE_IDCARD_FRONT);
                break;
                //身份证反面
            case R.id.img_idcard_reverse:
                img_icard_type  =1;
                IDCardCamera.create(OwnerInfoActivity.this).openCamera(IDCardCamera.TYPE_IDCARD_BACK);
                break;
            //提交车主信息
            case R.id.tv_bind:
                String account = etIdCard.getText().toString();
                if (StringUtil.isEmpty(etName.getText().toString())) {
                    showToast("请输入真实姓名");
                    return;
                }
                if (!IDCardValidate.validate_effective(account)) {
                    showToast("请输入正确的身份证号");
                    return;
                }
                if (StringUtil.isEmpty(etLicensePlate.getText().toString())) {
                    showToast("请输入车牌号码");
                    return;
                }
                if (mData!=null){
                    if (StringUtil.isEmpty(mData.getIdcard_front())&& StringUtil.isEmpty(idcardFrontPath)){
                        showToast("请拍摄或选择身份证人像面照片");
                        return;
                    }
                    if (StringUtil.isEmpty(mData.getIdcard_back()) && StringUtil.isEmpty(idcardReversePath)) {
                        showToast("请拍摄或选择身份证国徽面照片");
                        return;
                    }
                } else {
                    if (StringUtil.isEmpty(idcardFrontPath)) {
                        showToast("请拍摄或选择身份证人像面照片");
                        return;
                    }
                    if (StringUtil.isEmpty(idcardReversePath)) {
                        showToast("请拍摄或选择身份证国徽面照片");
                        return;
                    }
                }
                compressPhotos();
                break;
        }
    }

    /**
     * 压缩图片
     */
    private void compressPhotos() {
        mFileList = new ArrayList<>();
        List<IdCardBean> photos = new ArrayList<>();
        //正面
        if (!StringUtil.isEmpty(idcardFrontPath)){
            photos.add(new IdCardBean(idcardFrontPath,"idcard_front"));
        }
        //反面
        if (!StringUtil.isEmpty(idcardReversePath)){
            photos.add(new IdCardBean(idcardReversePath,"idcard_back"));
        }
        //无图片直接提交
        if (photos.size()  > 0) {
            //有图片则压缩
            final int[] i = {0};
            for (IdCardBean photo : photos) {
                Luban.with(this)
                        .load(photo.getPath())
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
                                mFileList.add(new SubmitIdCardBean(file,photo.getType()));
                                LogUtil.e("久速","第"+mFileList.size()+"张图");
                                if (i[0] == photos.size() - 1) {
                                    stopLoading();
                                    submit();
                                }
                                i[0]++;
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
        hashMap.put("user_name", etName.getText().toString().trim());
        hashMap.put("user_idcard", etIdCard.getText().toString().trim());
        hashMap.put("license_plate", etLicensePlate.getText().toString().trim());
        mPresenter.putBindInfo(PacketUtil.getRequestPacket(hashMap),mFileList);
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
    public void isFail(String msg) {
        showToast(msg);
    }

    @Override
    protected BindOwnerInfoPresenter getPresenter() {
        return new BindOwnerInfoPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_certification;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keyboardUtil != null && keyboardUtil.isShow()) {
            keyboardUtil.hideSoftInputMethod();
            keyboardUtil.hideKeyboard();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == IDCardCamera.RESULT_CODE) {
            //获取图片路径，显示图片
            final String path = IDCardCamera.getImagePath(data);
            if (!TextUtils.isEmpty(path)) {
                if (requestCode == IDCardCamera.TYPE_IDCARD_FRONT) {
                    //身份证正面
                    idcardFrontPath = path;
                    imgIdcardFront.setImageBitmap(BitmapFactory.decodeFile(path));

                } else if (requestCode == IDCardCamera.TYPE_IDCARD_BACK) {
                    //身份证反面
                    idcardReversePath = path;
                    imgIdcardReverse.setImageBitmap(BitmapFactory.decodeFile(path));
                }
            }
        }
    }



    public  class IdCardBean {

        public IdCardBean(String path, String type) {
            this.path = path;
            this.type = type;
        }
        /**
         * 图片路径
         */
        private String path;

        /**
         *  idcard_front  正面  idcard_back 反面
         */
        private String type;

        public String getPath() {
            return path == null ? "" : path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }


    }

    public  class SubmitIdCardBean {

        public SubmitIdCardBean(File file, String type) {
            this.file = file;
            this.type = type;
        }

        /**
         * 图片路径
         */
        private File file;
        /**
         *  idcard_front  正面  idcard_back 反面
         */
        private String type;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
