package com.jxs.hotspot.view.sort;

import com.jxs.hotspot.bean.BaseReq;
import com.jxs.hotspot.bean.SortResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class SortPresenter implements SortContract.Presenter {
    private SortContract.View mView;
    private ApiServer mApiServer;

    @Inject
    SortPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(SortContract.View view) {
        mView = view;
    }

    @Override
    public void loadSort() {
        BaseReq req = new BaseReq();
        mApiServer.getSort(req)
                .compose(RxUtil.<SortResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<SortResp>bindToLife())
                .subscribe(new BaseSubscriber<SortResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.loadSortFailure(e.message);
                    }

                    @Override
                    public void onNext(SortResp resp) {
                        Timber.e("success");
                        mView.loadSortSuccess(resp);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        Timber.tag("SortPresenter").e("unSubscribe");
        mView = null;
    }
}