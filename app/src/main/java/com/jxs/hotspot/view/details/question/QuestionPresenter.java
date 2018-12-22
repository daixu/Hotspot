package com.jxs.hotspot.view.details.question;

import com.jxs.hotspot.bean.BaseReq;
import com.jxs.hotspot.bean.QuestionListResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class QuestionPresenter implements QuestionContract.Presenter {
    private QuestionContract.View mView;
    private ApiServer mApiServer;

    @Inject
    QuestionPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(QuestionContract.View view) {
        this.mView = view;
    }

    @Override
    public void getQuestionList() {
        BaseReq req = new BaseReq();
        mApiServer.getQuestionList(req)
                .compose(RxUtil.<QuestionListResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<QuestionListResp>bindToLife())
                .subscribe(new BaseSubscriber<QuestionListResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getQuestionListFailure(e.message);
                    }

                    @Override
                    public void onNext(QuestionListResp resp) {
                        Timber.e("success");
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.getQuestionListSuccess(resp);
                            } else {
                                mView.getQuestionListFailure(resp.msgZh);
                            }
                        } else {
                            mView.getQuestionListFailure("fail");
                        }
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        Timber.tag("QuestionPresenter").e("unSubscribe");
        mView = null;
    }
}
