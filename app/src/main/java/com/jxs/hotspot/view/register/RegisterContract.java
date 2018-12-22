package com.jxs.hotspot.view.register;

import com.jxs.hotspot.base.BasePresenter;
import com.jxs.hotspot.base.BaseView;
import com.jxs.hotspot.bean.BaseResp;
import com.jxs.hotspot.bean.GetCodeReq;
import com.jxs.hotspot.bean.RegisterReq;

public class RegisterContract {

    interface Presenter extends BasePresenter {
        void takeView(RegisterContract.View view);

        void getPhoneCode(String areaCode, String phone);

        void getEmailCode(String email);

        void register(RegisterReq req);
    }

    interface View extends BaseView<RegisterContract.Presenter> {
        void startCountDown(int type);

        void getCodeFailure(String msg);

        void registerSuccess(BaseResp resp);

        void registerFailure(String msg);
    }
}
