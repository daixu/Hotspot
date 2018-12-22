package com.jxs.hotspot.view.login;

import com.jxs.hotspot.bean.LoginReq;
import com.jxs.hotspot.bean.LoginResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private ApiServer mApiServer;

    @Inject
    LoginPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(LoginContract.View view) {
        this.mView = view;
    }

    @Override
    public void login(LoginReq req) {
        mApiServer.loginPhone(req)
                .compose(RxUtil.<LoginResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<LoginResp>bindToLife())
                .subscribe(new BaseSubscriber<LoginResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.loginFailure(e.message);
                    }

                    @Override
                    public void onNext(LoginResp resp) {
                        Timber.e("success");
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.loginSuccess(resp);
                            } else {
                                mView.loginFailure(resp.msgZh);
                            }
                        } else {
                            mView.loginFailure("登录失败");
                        }
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
