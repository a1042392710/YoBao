package com.jjz.energy.ui.mine.information;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 修改简介
 * @author: create by chenhao on 2019/4/1
 */
public class ChangeDescActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_desc)
    EditText etDesc;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_change_desc;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("修改简介");
        tvToolbarRight.setText("保存");
        //输入原昵称
        String desc =  getIntent().getStringExtra("desc");
        etDesc.setText(desc);
    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //保存
            case R.id.tv_toolbar_right:
//                mPresenter.putMineInfo(PacketUtil.getRequestPacket(Utils.stringToMap("nickname",etNickName.getText().toString().trim())),"");
                break;
        }
    }

//    @Override
//    public void isSuccess(LoginBean data) {
//        LoginBean bean  = UserLoginBiz.getInstance(mContext).readUserInfo();
//        bean.setNickname(data.getNickname());
//        UserLoginBiz.getInstance(mContext).saveUserInfo(bean);
//        setResult(10,new Intent().putExtra("nick_name",etNickName.getText().toString().trim()));
//        finish();
//    }

//    @Override
//    public void isFail(String msg) {
//        showToast(msg);
//    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }
}
