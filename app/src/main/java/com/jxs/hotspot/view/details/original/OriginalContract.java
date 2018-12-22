package com.jxs.hotspot.view.details.original;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.OriginalListResp;

public class OriginalContract {

    interface Presenter extends BasePresenter {
        void takeView(OriginalContract.View view);

        void getOriginalList();
    }

    interface View extends BaseView<OriginalContract.Presenter> {

        void getOriginalListSuccess(OriginalListResp resp);

        void getOriginalListFailure(String msg);
    }
}
