package com.jjz.energy.ui.mine.information;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.presenter.mine.MineInformationPresenter;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.view.mine.IMineInfomationView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 修改昵称
 * @author: create by chenhao on 2019/4/1
 */
public class ChangeNickNameActivity extends BaseActivity <MineInformationPresenter> implements IMineInfomationView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_nick_name)
    EditText etNickName;

    @Override
    protected MineInformationPresenter getPresenter() {
        return new MineInformationPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_change_nickname;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("修改昵称");
        tvToolbarRight.setText("保存");
        //输入原昵称
        String nickName =  getIntent().getStringExtra("nick_name");
        etNickName.setText(nickName);
    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //保存
            case R.id.tv_toolbar_right:
                mPresenter.putUserInfo(PacketUtil.getRequestPacket(Utils.stringToMap("nickname",etNickName.getText().toString().trim())),"");
                break;
        }
    }

    @Override
    public void isSuccess(UserInfo data) {
        UserInfo bean  = UserLoginBiz.getInstance(mContext).readUserInfo();
        bean.setNickname(data.getNickname());
        UserLoginBiz.getInstance(mContext).saveUserInfo(bean);
        setResult(10,new Intent().putExtra("nick_name",etNickName.getText().toString().trim()));
        finish();
    }

    @Override
    public void isFail(String msg ,boolean isNetAndServiceError) {
        showToast(msg);
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
