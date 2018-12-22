package com.jxs.hotspot.view.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.LoginReq;
import com.jxs.hotspot.bean.LoginResp;
import com.jxs.hotspot.bean.RegionCodeResp;
import com.jxs.hotspot.utils.MD5;
import com.jxs.hotspot.utils.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

@Route(path = "/jxs/login")
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_email)
    EditText mEditEmail;
    @BindView(R.id.layout_edit_phone)
    LinearLayout mLayoutEditPhone;
    @BindView(R.id.layout_email_1)
    LinearLayout mLayoutEmail1;
    @BindView(R.id.layout_email_2)
    LinearLayout mLayoutEmail2;
    @BindView(R.id.layout_phone_1)
    LinearLayout mLayoutPhone1;
    @BindView(R.id.layout_phone_2)
    LinearLayout mLayoutPhone2;
    @BindView(R.id.edit_pwd)
    EditText mEditPwd;
    @BindView(R.id.text_register)
    TextView mTextRegister;
    @BindView(R.id.text_forget_pwd)
    TextView mTextForgetPwd;
    @BindView(R.id.text_code)
    TextView mTextCode;

    @Inject
    LoginContract.Presenter mPresenter;

    private int mType = 1;

    private String mCode = "86";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mTextRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTextForgetPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //设置状态栏的颜色为灰色
        // Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.color_BDBDBD));
        // Eyes.translucentStatusBar(this, true);

        mPresenter.takeView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            Timber.e("------------空--------------");
            return;
        }

        if (requestCode == 100 && resultCode == RESULT_OK) {
            RegionCodeResp.DataBean dataBean = (RegionCodeResp.DataBean) data.getSerializableExtra("dataBean");
            Timber.e("dataBean= " + dataBean.countryName);

            mCode = dataBean.countryCode;
            mTextCode.setText("+".concat(mCode));
        }
    }

    @OnClick({R.id.layout_email_2, R.id.layout_phone_2, R.id.text_register, R.id.text_forget_pwd,
            R.id.layout_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_email_2: {
                mLayoutEmail1.setVisibility(View.VISIBLE);
                mLayoutPhone1.setVisibility(View.GONE);
                mLayoutEmail2.setVisibility(View.GONE);
                mLayoutPhone2.setVisibility(View.VISIBLE);
                mEditEmail.setVisibility(View.VISIBLE);
                mLayoutEditPhone.setVisibility(View.GONE);

                mType = 2;
            }
            break;
            case R.id.layout_phone_2: {
                mLayoutEmail1.setVisibility(View.GONE);
                mLayoutPhone1.setVisibility(View.VISIBLE);
                mLayoutEmail2.setVisibility(View.VISIBLE);
                mLayoutPhone2.setVisibility(View.GONE);
                mEditEmail.setVisibility(View.GONE);
                mLayoutEditPhone.setVisibility(View.VISIBLE);

                mType = 1;
            }
            break;
            case R.id.text_register: {
                ARouter.getInstance().build("/jxs/register").navigation();
            }
            break;
            case R.id.text_forget_pwd: {
                ARouter.getInstance().build("/jxs/forget/pwd").navigation();
            }
            break;
            case R.id.layout_code: {
                ARouter.getInstance().build("/jxs/region/code").navigation(LoginActivity.this, 100);
            }
            break;
            case R.id.btn_login: {
                login();
            }
            break;
            default:
                break;
        }
    }

    private void login() {
        String phone = mEditPhone.getText().toString();
        String email = mEditEmail.getText().toString();
        String pwd = mEditPwd.getText().toString();
        if (verify(phone, email, pwd)) {
            LoginReq req = new LoginReq();
            if (mType == 1) {
                req.account = phone;
                req.password = MD5.encrypt(pwd);
            } else {
                req.account = email;
                req.password = MD5.encrypt(pwd);
            }
            mPresenter.login(req);
        }
    }

    private boolean verify(String phone, String email, String pwd) {
        if (mType == 1) {
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showTip(LoginActivity.this, "账号输入不能为空");
                return false;
            }
        } else {
            if (TextUtils.isEmpty(email)) {
                ToastUtil.showTip(LoginActivity.this, "账号输入不能为空");
                return false;
            }
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showTip(LoginActivity.this, "密码输入不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void loginSuccess(LoginResp resp) {
        ToastUtil.showTip(this, "登录成功");
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        Timber.e("loginFailure msg= " + msg);
        ToastUtil.showTip(this, msg);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
