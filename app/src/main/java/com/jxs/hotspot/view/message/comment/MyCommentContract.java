package com.jxs.hotspot.view.message.comment;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.BaseResp;

public class MyCommentContract {

    interface Presenter extends BasePresenter {
        void takeView(MyCommentContract.View view);

        void getMyCommentList();
    }

    interface View extends BaseView<MyCommentContract.Presenter> {

        void getMyCommentListSuccess(BaseResp resp);

        void getMyCommentListFailure(String msg);
    }
}
