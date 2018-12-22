package com.jxs.hotspot.view.details.recommend;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.OriginalListResp;

public class RecommendContract {

    interface Presenter extends BasePresenter {
        void takeView(RecommendContract.View view);

        void getOriginalList();
    }

    interface View extends BaseView<RecommendContract.Presenter> {

        void getOriginalListSuccess(OriginalListResp resp);

        void getOriginalListFailure(String msg);
    }
}
