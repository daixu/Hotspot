package com.jxs.hotspot.view.sort;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.SortListResp;
import com.jxs.hotspot.bean.SortResp;

public class SortListContract {

    interface Presenter extends BasePresenter {
        void takeView(SortListContract.View view);

        void loadSortList();
    }

    interface View extends BaseView<Presenter> {

        void loadSortListSuccess(SortListResp resp);

        void loadSortListFailure(String msg);
    }
}
