package com.jxs.hotspot.view.feedback;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;

@Route(path = "/jxs/feedback")
public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        showBackButton();
        setTitleText("客户反馈");
        showTitleLine();
    }
}
