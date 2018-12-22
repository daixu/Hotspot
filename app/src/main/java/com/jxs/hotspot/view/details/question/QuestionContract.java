package com.jxs.hotspot.view.details.question;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.CommentListResp;
import com.jxs.hotspot.bean.FindDetailResp;
import com.jxs.hotspot.bean.QuestionListResp;
import com.jxs.hotspot.bean.UserDetailCountResp;

public class QuestionContract {

    interface Presenter extends BasePresenter {
        void takeView(QuestionContract.View view);

        void getQuestionList();
    }

    interface View extends BaseView<QuestionContract.Presenter> {
        void getQuestionListSuccess(QuestionListResp resp);

        void getQuestionListFailure(String msg);
    }
}
