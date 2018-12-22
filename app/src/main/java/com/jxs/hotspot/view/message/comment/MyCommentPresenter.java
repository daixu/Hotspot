package com.jxs.hotspot.view.message.comment;

import com.jxs.hotspot.bean.BaseResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class MyCommentPresenter implements MyCommentContract.Presenter {
    private MyCommentContract.View mView;
    private ApiServer mApiServer;

    @Inject
    MyCommentPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(MyCommentContract.View view) {
        this.mView = view;
    }

    @Override
    public void getMyCommentList() {
        mApiServer.getMyComment()
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
                        mView.getMyCommentListFailure(e.message);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        Timber.e("success");
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.getMyCommentListSuccess(resp);
                            } else {
                                mView.getMyCommentListFailure(resp.msgZh);
                            }
                        } else {
                            mView.getMyCommentListFailure("获取失败");
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
