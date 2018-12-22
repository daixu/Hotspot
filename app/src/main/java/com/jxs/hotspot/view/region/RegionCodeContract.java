package com.jxs.hotspot.view.region;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.RegionCodeResp;

public class RegionCodeContract {

    interface Presenter extends BasePresenter {
        void takeView(RegionCodeContract.View view);

        void regionCode();
    }

    interface View extends BaseView<RegionCodeContract.Presenter> {

        void regionCodeSuccess(RegionCodeResp resp);

        void regionCodeFailure(String msg);
    }
}
