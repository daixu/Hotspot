package com.jxs.hotspot.view.register;

import com.jxs.hotspot.bean.BaseResp;
import com.jxs.hotspot.bean.RegisterReq;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;
    private ApiServer mApiServer;

    @Inject
    RegisterPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(RegisterContract.View view) {
        this.mView = view;
    }

    @Override
    public void getPhoneCode(String areaCode, String phone) {
        mApiServer.getPhoneCode(areaCode, phone)
                .compose(RxUtil.<BaseResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<BaseResp>bindToLife())
                .subscribe(new BaseSubscriber<BaseResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getCodeFailure(e.message);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.startCountDown(1);
                            } else {
                                mView.getCodeFailure(resp.msgZh);
                            }
                        } else {
                            mView.getCodeFailure("验证码发送失败");
                        }
                    }
                });
    }

    @Override
    public void getEmailCode(String email) {
        mApiServer.getEmailCode(email)
                .compose(RxUtil.<BaseResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<BaseResp>bindToLife())
                .subscribe(new BaseSubscriber<BaseResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getCodeFailure(e.message);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.startCountDown(2);
                            } else {
                                mView.getCodeFailure(resp.msgZh);
                            }
                        } else {
                            mView.getCodeFailure("验证码发送失败");
                        }
                    }
                });
    }

    @Override
    public void register(RegisterReq req) {
        mApiServer.registerPhone(req)
                .compose(RxUtil.<BaseResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<BaseResp>bindToLife())
                .subscribe(new BaseSubscriber<BaseResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.registerFailure(e.message);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        Timber.e("success");
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.registerSuccess(resp);
                            } else {
                                mView.registerFailure(resp.msgZh);
                            }
                        } else {
                            mView.registerFailure("注册失败");
                        }
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        Timber.tag("RegisterPresenter").e("unSubscribe");
        mView = null;
    }
}
