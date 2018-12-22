package com.jxs.hotspot.view.settings;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/jxs/setting")
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        showBackButton();
        setTitleText("系统设置");
        showTitleLine();
    }

    @OnClick({R.id.layout_avatar, R.id.layout_cache, R.id.layout_about, R.id.layout_version,
            R.id.layout_recommend, R.id.layout_disclaimer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_avatar:
                break;
            case R.id.layout_cache:
                break;
            case R.id.layout_about: {
                ARouter.getInstance().build("/jxs/feedback").navigation();
            }
            break;
            case R.id.layout_version:
                break;
            case R.id.layout_recommend:
                break;
            case R.id.layout_disclaimer:
                break;
            default:
                break;
        }
    }
}
