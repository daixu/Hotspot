package com.jxs.hotspot.view.details;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.BaseResp;
import com.jxs.hotspot.bean.CommentListResp;
import com.jxs.hotspot.bean.FindDetailResp;
import com.jxs.hotspot.bean.RewardInfoResp;
import com.jxs.hotspot.bean.RewardReq;
import com.jxs.hotspot.bean.UserDetailCountResp;

public class DetailContract {

    interface Presenter extends BasePresenter {
        void takeView(DetailContract.View view);

        void getDetails();

        void getComment();

        void getRewardInfo();

        void reward(String money);

        void getUserDetailCount();
    }

    interface View extends BaseView<DetailContract.Presenter> {
        void getDetailsSuccess(FindDetailResp.DataBean dataBean);

        void getDetailsFailure(String msg);

        void getCommentSuccess(CommentListResp resp);

        void getCommentFailure(String msg);

        void getRewardInfoSuccess(RewardInfoResp resp);

        void getRewardInfoFailure(String msg);

        void rewardSuccess(BaseResp resp);

        void rewardFailure(String msg);

        void getUserDetailCountSuccess(UserDetailCountResp resp);

        void getUserDetailCountFailure(String msg);
    }
}
