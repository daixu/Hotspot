package com.jxs.hotspot.view.details.recommend;

import com.jxs.hotspot.bean.BaseReq;
import com.jxs.hotspot.bean.OriginalListResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;
import com.jxs.hotspot.view.details.original.OriginalContract;

import javax.inject.Inject;

import timber.log.Timber;

public class RecommendPresenter implements RecommendContract.Presenter {
    private RecommendContract.View mView;
    private ApiServer mApiServer;

    @Inject
    RecommendPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(RecommendContract.View view) {
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
