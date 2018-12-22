package com.jxs.hotspot.view.sort;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.SortResp;

public class SortContract {

    interface Presenter extends BasePresenter {
        void takeView(SortContract.View view);

        void loadSort();
    }

    interface View extends BaseView<Presenter> {

        void loadSortSuccess(SortResp resp);

        void loadSortFailure(String msg);
    }
}
