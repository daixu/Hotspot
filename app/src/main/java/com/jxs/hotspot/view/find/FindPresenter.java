package com.jxs.hotspot.view.find;

import com.jxs.hotspot.bean.BannerResp;
import com.jxs.hotspot.bean.BaseReq;
import com.jxs.hotspot.bean.FindListResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class FindPresenter implements FindContract.Presenter {
    private FindContract.View mView;
    private ApiServer mApiServer;

    @Inject
    FindPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(FindContract.View view) {
        mView = view;
    }

    @Override
    public void loadBanner() {
        mApiServer.getBanner()
                .compose(RxUtil.<BannerResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<BannerResp>bindToLife())
                .subscribe(new BaseSubscriber<BannerResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.loadBannerFailure(e.message);
                    }

                    @Override
                    public void onNext(BannerResp resp) {
                        Timber.e("success");
                        mView.loadBannerSuccess(resp);
                    }
                });
    }

    @Override
    public void loadFindList() {
        BaseReq req = new BaseReq();
        mApiServer.getFindList(req)
                .compose(RxUtil.<FindListResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<FindListResp>bindToLife())
                .subscribe(new BaseSubscriber<FindListResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.loadFindListFailure(e.message);
                    }

                    @Override
                    public void onNext(FindListResp resp) {
                        Timber.e("success");
                        mView.loadFindListSuccess(resp);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        Timber.tag("FindPresenter").e("unSubscribe");
        mView = null;
    }
}
