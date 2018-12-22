package com.jxs.hotspot.view.pwd;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jxs.hotspot.common.Constant.EventType.EVENT_RESET_LOGIN_PWD_TYPE;

@Route(path = "/jxs/reset/pwd")
public class ResetPwdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);

        showBackButton();
        setTitleText("重新设置密码");
        showTitleLine();
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        EventBus.getDefault().post(new MessageEvent(EVENT_RESET_LOGIN_PWD_TYPE, "finish"));
    }
}
