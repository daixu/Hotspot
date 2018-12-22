package com.jxs.hotspot.view.details;

import com.jxs.hotspot.bean.BaseReq;
import com.jxs.hotspot.bean.BaseResp;
import com.jxs.hotspot.bean.CommentListResp;
import com.jxs.hotspot.bean.FindDetailResp;
import com.jxs.hotspot.bean.RewardInfoResp;
import com.jxs.hotspot.bean.RewardReq;
import com.jxs.hotspot.bean.UserDetailCountResp;
import com.jxs.hotspot.net.BaseSubscriber;
import com.jxs.hotspot.net.ExceptionHandle;
import com.jxs.hotspot.net.api.service.ApiServer;
import com.jxs.hotspot.utils.RxUtil;

import javax.inject.Inject;

import timber.log.Timber;

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;
    private ApiServer mApiServer;

    @Inject
    DetailPresenter(ApiServer apiServer) {
        mApiServer = apiServer;
    }

    @Override
    public void takeView(DetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void getDetails() {
        BaseReq req = new BaseReq();
        mApiServer.getDetail(req)
                .compose(RxUtil.<FindDetailResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<FindDetailResp>bindToLife())
                .subscribe(new BaseSubscriber<FindDetailResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getDetailsFailure(e.message);
                    }

                    @Override
                    public void onNext(FindDetailResp resp) {
                        Timber.e("success");
                        if (null != resp) {
                            if (resp.isOk() && null != resp.data) {
                                mView.getDetailsSuccess(resp.data);
                            } else {
                                mView.getDetailsFailure(resp.msgZh);
                            }
                        } else {
                            mView.getDetailsFailure("fail");
                        }
                    }
                });
    }

    @Override
    public void getComment() {
        BaseReq req = new BaseReq();
        mApiServer.getCommentList(req)
                .compose(RxUtil.<CommentListResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<CommentListResp>bindToLife())
                .subscribe(new BaseSubscriber<CommentListResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getCommentFailure(e.message);
                    }

                    @Override
                    public void onNext(CommentListResp resp) {
                        Timber.e("success");
                        if (null != resp && resp.isOk()) {
                            mView.getCommentSuccess(resp);
                        }
                    }
                });
    }

    @Override
    public void getRewardInfo() {
        BaseReq req = new BaseReq();
        mApiServer.getRewardInfo(req)
                .compose(RxUtil.<RewardInfoResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<RewardInfoResp>bindToLife())
                .subscribe(new BaseSubscriber<RewardInfoResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getRewardInfoFailure(e.message);
                    }

                    @Override
                    public void onNext(RewardInfoResp resp) {
                        Timber.e("success");
                        if (null != resp && resp.isOk()) {
                            mView.getRewardInfoSuccess(resp);
                        }
                    }
                });
    }

    @Override
    public void reward(String money) {
        RewardReq req = new RewardReq();
        req.money = money;
        mApiServer.reward(req)
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
                        mView.rewardFailure(e.message);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        Timber.e("success");
                        if (null != resp) {
                            if (resp.isOk()) {
                                mView.rewardSuccess(resp);
                            } else {
                                mView.rewardFailure(resp.msgZh);
                            }
                        } else {
                            mView.rewardFailure("failure");
                        }
                    }
                });
    }

    @Override
    public void getUserDetailCount() {
        BaseReq req = new BaseReq();
        mApiServer.getUserDetailCount()
                .compose(RxUtil.<UserDetailCountResp>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER_BACK_PRESSURE))
                .compose(mView.<UserDetailCountResp>bindToLife())
                .subscribe(new BaseSubscriber<UserDetailCountResp>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        mView.getUserDetailCountFailure(e.message);
                    }

                    @Override
                    public void onNext(UserDetailCountResp resp) {
                        Timber.e("success");
                        if (null != resp && resp.isOk()) {
                            mView.getUserDetailCountSuccess(resp);
                        }
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
