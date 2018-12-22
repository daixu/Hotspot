package com.jxs.hotspot.view.details.original;

import com.jxs.hotspot.bean.BaseReq;
import com.jxs.hotspot.bean.OriginalListResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class OriginalPresenter implements OriginalContract.Presenter {
    private OriginalContract.View mView;
    private ApiServer mApiServer;

    @Inject
    OriginalPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(OriginalContract.View view) {
        this.mView = view;
    }

    @Override
    public void getOriginalList() {
        BaseReq req = new BaseReq();
        mApiServer.getOriginalList()
                .compose(RxUtil.<OriginalListResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<OriginalListResp>bindToLife())
                .subscribe(new BaseSubscriber<OriginalListResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getOriginalListFailure(e.message);
                    }

                    @Override
                    public void onNext(OriginalListResp resp) {
                        Timber.e("success");
                        mView.getOriginalListSuccess(resp);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        Timber.tag("DetailPresenter").e("unSubscribe");
        mView = null;
    }
}
