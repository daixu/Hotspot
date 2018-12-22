package com.jxs.hotspot.view.sort;

import com.jxs.hotspot.bean.BaseReq;
import com.jxs.hotspot.bean.SortListResp;
import com.jxs.hotspot.bean.SortResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class SortListPresenter implements SortListContract.Presenter {
    private SortListContract.View mView;
    private ApiServer mApiServer;

    @Inject
    SortListPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(SortListContract.View view) {
        mView = view;
    }

    @Override
    public void loadSortList() {
        BaseReq req = new BaseReq();
        mApiServer.getSortList(req)
                .compose(RxUtil.<SortListResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<SortListResp>bindToLife())
                .subscribe(new BaseSubscriber<SortListResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.loadSortListFailure(e.message);
                    }

                    @Override
                    public void onNext(SortListResp resp) {
                        Timber.e("success");
                        mView.loadSortListSuccess(resp);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        Timber.tag("SortListPresenter").e("unSubscribe");
        mView = null;
    }
}