package com.jjz.energy.ui.mine.information;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.BindOwnerInfoBean;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.presenter.mine.BindOwnerInfoPresenter;
import com.jjz.energy.util.IDCardValidate;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.KeyboardUtil;
import com.jjz.energy.view.mine.IBindOwnerInfoView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 车辆认证
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
    @BindView(R.id.tv_bind_warm_prompt)
    TextView tvBindWarmPrompt;

    private KeyboardUtil keyboardUtil;

    @Override
    protected BindOwnerInfoPresenter getPresenter() {
        return new BindOwnerInfoPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_owner_info;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        tvToolbarTitle.setText("车辆认证");
        //输入框触摸事件
        etLicensePlate.setOnTouchListener((view, event) -> {
            disMissSoftKeyboard();
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



    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_bind })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
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
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("user_name", etName.getText().toString().trim());
                hashMap.put("user_idcard", etIdCard.getText().toString().trim());
                hashMap.put("license_plate", etLicensePlate.getText().toString().trim());
                mPresenter.putBindInfo(PacketUtil.getRequestPacket(hashMap));
                break;
        }
    }

    @Override
    public void isPutSuccess(String data) {
        UserInfo loginBean = UserLoginBiz.getInstance(mContext).readUserInfo();
        loginBean.setLicense_plate(etLicensePlate.getText().toString().trim());
        UserLoginBiz.getInstance(mContext).saveUserInfo(loginBean);
        showToast("提交成功");
        finish();
    }

    @Override
    public void isGetSuccess(BindOwnerInfoBean data) {
        //写入车主信息
        etIdCard.setText(data.getUser_idcard());
        etName.setText(data.getUser_name());
        etLicensePlate.setText(data.getLicense_plate());
    }

    @Override
    public void isFail(String msg ,boolean isNetAndServiceError) {
        showToast(msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    if (keyboardUtil!=null&&keyboardUtil.isShow()){
        keyboardUtil.hideSoftInputMethod();
        keyboardUtil.hideKeyboard();
    }
    }
}
