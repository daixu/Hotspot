package com.jxs.hotspot.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxs.hotspot.HtApplication;
import com.jxs.hotspot.R;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class BaseActivity extends RxAppCompatActivity {

    protected ImageView sImgBtn;

    protected View mViewLine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HtApplication.getInstance().addActivity(this);
        if (enableSlideClose()) {
            initSlider();
        }

        if (enableLightStatusBar()) {
            setLightStatusBar();
        }
        // StatusBarCompat.translucentStatusBar(this, true);
        // Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
    }

    public boolean enableSlideClose() {
        return true;
    }

    private void initSlider() {
        SlidrConfig config = new SlidrConfig.Builder()
//                .primaryColor(ContextCompat.getColor(this, R.color.colorAccent))
//                .secondaryColor(ContextCompat.getColor(this, R.color.colorAccent))
                .position(SlidrPosition.LEFT)
                .edge(true)
                .edgeSize(0.2f)
                .build();
        Slidr.attach(this, config);
    }

    private boolean enableLightStatusBar() {
        return true;
    }

    private void setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    /*******************************************************
     * 说明：快速设置标题名，标题组件的的id必须为text_title;
     ********************************************************/
    protected void setTitleText(CharSequence text) {
        TextView textView = findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(text);
        }
    }

    /*******************************************************
     * 说明：快速设置标题名，标题组件的的id必须为text_title;
     * @param resId
     ********************************************************/
    protected void setTitleText(int resId) {
        TextView textView = findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(resId);
        }
    }

    protected void showBackButton() {
        sImgBtn = findViewById(R.id.img_back);
        if (sImgBtn != null) {
            sImgBtn.setVisibility(View.VISIBLE);
        }
    }

    protected void showTitleLine() {
        mViewLine = findViewById(R.id.view_title_line);
        if (null != mViewLine) {
            mViewLine.setVisibility(View.VISIBLE);
        }
    }

    public void doBack(View v) {
        finish();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            createConfigurationContext(config);
        } else {
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    protected void onDestroy() {
        HtApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }
}