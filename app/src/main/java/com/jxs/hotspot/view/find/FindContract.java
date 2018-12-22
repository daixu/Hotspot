package com.jxs.hotspot.view.find;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.BannerResp;
import com.jxs.hotspot.bean.FindListResp;

public class FindContract {

    interface Presenter extends BasePresenter {
        void takeView(FindContract.View view);

        void loadBanner();

        void loadFindList();
    }

    interface View extends BaseView<Presenter> {

        void loadBannerSuccess(BannerResp resp);

        void loadBannerFailure(String msg);

        void loadFindListSuccess(FindListResp resp);

        void loadFindListFailure(String msg);
    }
}
