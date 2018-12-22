package com.jxs.hotspot.view.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.BaseResp;
import com.jxs.hotspot.bean.GetCodeReq;
import com.jxs.hotspot.bean.RegionCodeResp;
import com.jxs.hotspot.bean.RegisterReq;
import com.jxs.hotspot.utils.MD5;
import com.jxs.hotspot.utils.NetworkUtil;
import com.jxs.hotspot.utils.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

@Route(path = "/jxs/register")
public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    @BindView(R.id.layout_phone_1)
    LinearLayout mLayoutPhone1;
    @BindView(R.id.layout_phone_2)
    LinearLayout mLayoutPhone2;
    @BindView(R.id.layout_email_1)
    LinearLayout mLayoutEmail1;
    @BindView(R.id.layout_email_2)
    LinearLayout mLayoutEmail2;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.layout_edit_email)
    LinearLayout mLayoutEditEmail;
    @BindView(R.id.edit_email)
    EditText mEditEmail;
    @BindView(R.id.edit_ver_code)
    EditText mEditVerCode;
    @BindView(R.id.edit_pwd)
    EditText mEditPwd;
    @BindView(R.id.edit_confirm_pwd)
    EditText mEditConfirmPwd;
    @BindView(R.id.layout_edit_phone)
    LinearLayout mLayoutEditPhone;
    @BindView(R.id.text_code)
    TextView mTextCode;

    @Inject
    RegisterContract.Presenter mPresenter;
    @BindView(R.id.btn_send_ver_code)
    Button mBtnSendVerCode;
    @BindView(R.id.btn_mail_ver_code)
    Button mBtnMailVerCode;
    @BindView(R.id.check_user_agreement)
    CheckBox mCheckUserAgreement;

    // 1 手机号码注册 2 邮箱注册
    private int mType = 1;
    private String mCode = "86";
    private String mCountryName = "中国";

    private boolean isCountDown = false;
    private boolean isEmailCountDown = false;

    private boolean mIsChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        showBackButton();
        setTitleText("注册");
        showTitleLine();

        addListener();
        mPresenter.takeView(this);
    }

    private void addListener() {
        mCheckUserAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mIsChecked = isChecked;
            }
        });
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
            mCountryName = dataBean.countryName;
            mCode = dataBean.countryCode;

            mTextCode.setText("+".concat(mCode));
        }
    }

    @OnClick({R.id.layout_phone_1, R.id.layout_phone_2, R.id.layout_email_1, R.id.layout_email_2,
            R.id.layout_code, R.id.btn_send_ver_code, R.id.btn_mail_ver_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_phone_2: {
                mLayoutEmail1.setVisibility(View.GONE);
                mLayoutPhone1.setVisibility(View.VISIBLE);
                mLayoutEmail2.setVisibility(View.VISIBLE);
                mLayoutPhone2.setVisibility(View.GONE);

                mLayoutEditEmail.setVisibility(View.GONE);
                mEditVerCode.setHint("验证码");
                mLayoutEditPhone.setVisibility(View.VISIBLE);

                mType = 1;
            }
            break;
            case R.id.layout_email_2: {
                mLayoutEmail1.setVisibility(View.VISIBLE);
                mLayoutPhone1.setVisibility(View.GONE);
                mLayoutEmail2.setVisibility(View.GONE);
                mLayoutPhone2.setVisibility(View.VISIBLE);

                mLayoutEditEmail.setVisibility(View.VISIBLE);
                mEditVerCode.setHint("请输入6位数的邮箱验证码");
                mLayoutEditPhone.setVisibility(View.GONE);

                mType = 2;
            }
            break;
            case R.id.layout_code: {
                ARouter.getInstance().build("/jxs/region/code").navigation(RegisterActivity.this, 100);
            }
            break;
            case R.id.btn_send_ver_code: {
                getSmsCode();
            }
            break;
            case R.id.btn_mail_ver_code: {
                getEmailCode();
            }
            break;
            case R.id.btn_register: {
                if (mIsChecked) {
                    register();
                } else {
                    ToastUtil.showTip(RegisterActivity.this, "请先同意用户协议");
                }
            }
            break;
            default:
                break;
        }
    }

    private void getSmsCode() {
        String phone = mEditPhone.getText().toString().trim();
        if (!NetworkUtil.isAvailable(this)) {
            ToastUtil.showTip(this, R.string.network_error);
            return;
        }
        if (phone.length() < 11) {
            ToastUtil.showTip(this, R.string.user_error_hint_phone);
            return;
        }
        if (!isCountDown) {
            mPresenter.getPhoneCode(mCode, phone);
        }
    }

    private void getEmailCode() {
        String email = mEditEmail.getText().toString().trim();
        if (!NetworkUtil.isAvailable(this)) {
            ToastUtil.showTip(this, R.string.network_error);
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ToastUtil.showTip(this, "账号输入不能为空");
            return;
        }
        if (!isEmailCountDown) {
            GetCodeReq req = new GetCodeReq();
            req.account = email;
            mPresenter.getEmailCode(email);
        }
    }

    private CountDownTimer mVerificationTimer = new CountDownTimer(60 * 1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mBtnSendVerCode.setEnabled(false);
            mBtnSendVerCode.setText(getString(R.string.account_auth_cd_format, millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            mBtnSendVerCode.setEnabled(true);
            mBtnSendVerCode.setText(getString(R.string.account_auth_re_get));
            isCountDown = false;
        }
    };

    private CountDownTimer mEmailVerificationTimer = new CountDownTimer(60 * 1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mBtnMailVerCode.setEnabled(false);
            mBtnMailVerCode.setText(getString(R.string.account_auth_cd_format, millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            mBtnMailVerCode.setEnabled(true);
            mBtnMailVerCode.setText(getString(R.string.account_auth_re_get));
            isEmailCountDown = false;
        }
    };

    private void register() {
        String phone = mEditPhone.getText().toString();
        String verCode = mEditVerCode.getText().toString();
        String email = mEditEmail.getText().toString();
        String pwd = mEditPwd.getText().toString();
        String confirmPwd = mEditConfirmPwd.getText().toString();

        if (verify(phone, verCode, email, pwd, confirmPwd)) {
            RegisterReq req = new RegisterReq();
            // 1 手机号码注册 2 邮箱注册
            if (mType == 1) {
                req.account = phone;
                req.areaCode = mCode;
                req.code = verCode;
            } else {
                req.account = email;
                req.code = verCode;
                mPresenter.register(req);
            }
            req.countryName = mCountryName;
            req.password = MD5.encrypt(pwd);
            req.rePassword = MD5.encrypt(confirmPwd);
            mPresenter.register(req);
        }
    }

    private boolean verify(String phone, String verCode, String email, String pwd, String confirmPwd) {
        if (mType == 1) {
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showTip(RegisterActivity.this, "账号输入不能为空");
                return false;
            }
        } else {
            if (TextUtils.isEmpty(email)) {
                ToastUtil.showTip(RegisterActivity.this, "账号输入不能为空");
                return false;
            }
        }
        if (TextUtils.isEmpty(verCode)) {
            ToastUtil.showTip(RegisterActivity.this, "验证码输入不能为空");
            return false;
        }
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)) {
            ToastUtil.showTip(RegisterActivity.this, "密码输入不能为空");
            return false;
        }
        if (!pwd.equals(confirmPwd)) {
            ToastUtil.showTip(RegisterActivity.this, "密码输入不一致");
            return false;
        }
        return true;
    }

    @Override
    public void startCountDown(int type) {
        // 1 手机号码注册 2 邮箱注册
        if (type == 1) {
            isCountDown = true;
            mBtnSendVerCode.setEnabled(false);
            mVerificationTimer.start();
        } else {
            isEmailCountDown = true;
            mBtnMailVerCode.setEnabled(false);
            mEmailVerificationTimer.start();
        }
    }

    @Override
    public void getCodeFailure(String msg) {
        Timber.e("getCodeFailure msg= " + msg);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showTip(this, msg);
        }
    }

    @Override
    public void registerSuccess(BaseResp resp) {
        ToastUtil.showTip(this, resp.msgZh);
        finish();
    }

    @Override
    public void registerFailure(String msg) {
        Timber.e("registerFailure msg= " + msg);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mVerificationTimer.onFinish();
        mVerificationTimer.cancel();

        mEmailVerificationTimer.onFinish();
        mEmailVerificationTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVerificationTimer.onFinish();
        mVerificationTimer.cancel();

        mEmailVerificationTimer.onFinish();
        mEmailVerificationTimer.cancel();
    }
}
