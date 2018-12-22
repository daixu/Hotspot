package com.jxs.hotspot.view.search;

import com.jxs.hotspot.bean.SearchHotWordResp;
import com.jxs.hotspot.bean.SearchReq;
import com.jxs.hotspot.bean.SearchResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private ApiServer mApiServer;

    @Inject
    SearchPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(SearchContract.View view) {
        this.mView = view;
    }

    @Override
    public void getSearchHotWord() {
        mApiServer.getSearchHotWord()
                .compose(RxUtil.<SearchHotWordResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<SearchHotWordResp>bindToLife())
                .subscribe(new BaseSubscriber<SearchHotWordResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getSearchHotWordFailure(e.message);
                    }

                    @Override
                    public void onNext(SearchHotWordResp resp) {
                        Timber.e("success");
                        mView.getSearchHotWordSuccess(resp);
                    }
                });
    }

    @Override
    public void search(String keywords) {
        SearchReq req = new SearchReq();
        req.keywords = keywords;
        mApiServer.search(req)
                .compose(RxUtil.<SearchResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<SearchResp>bindToLife())
                .subscribe(new BaseSubscriber<SearchResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getSearchListFailure(e.message);
                    }

                    @Override
                    public void onNext(SearchResp resp) {
                        Timber.e("success");
                        mView.getSearchListSuccess(resp);
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
