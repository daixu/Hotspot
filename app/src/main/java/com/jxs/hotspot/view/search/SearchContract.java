package com.jxs.hotspot.view.search;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.SearchHotWordResp;
import com.jxs.hotspot.bean.SearchResp;

public class SearchContract {

    interface Presenter extends BasePresenter {
        void takeView(SearchContract.View view);

        void getSearchHotWord();

        void search(String keywords);
    }

    interface View extends BaseView<SearchContract.Presenter> {

        void getSearchHotWordSuccess(SearchHotWordResp resp);

        void getSearchHotWordFailure(String msg);

        void getSearchListSuccess(SearchResp resp);

        void getSearchListFailure(String msg);
    }
}
