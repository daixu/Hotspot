package com.jxs.hotspot.view.region;

import com.jxs.hotspot.bean.RegionCodeResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class RegionCodePresenter implements RegionCodeContract.Presenter {
    private RegionCodeContract.View mView;
    private ApiServer mApiServer;

    @Inject
    RegionCodePresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(RegionCodeContract.View view) {
        this.mView = view;
    }

    @Override
    public void regionCode() {
        mApiServer.regionCode()
                .compose(RxUtil.<RegionCodeResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<RegionCodeResp>bindToLife())
                .subscribe(new BaseSubscriber<RegionCodeResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.regionCodeFailure(e.message);
                    }

                    @Override
                    public void onNext(RegionCodeResp resp) {
                        Timber.e("success");
                        mView.regionCodeSuccess(resp);
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
