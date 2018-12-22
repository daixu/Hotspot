package com.jxs.hotspot.view.login;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.LoginReq;
import com.jxs.hotspot.bean.LoginResp;

public class LoginContract {

    interface Presenter extends BasePresenter {
        void takeView(LoginContract.View view);

        void login(LoginReq req);
    }

    interface View extends BaseView<LoginContract.Presenter> {

        void loginSuccess(LoginResp resp);

        void loginFailure(String msg);
    }
}
