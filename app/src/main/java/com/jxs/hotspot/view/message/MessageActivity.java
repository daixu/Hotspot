package com.jxs.hotspot.view.message;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

@Route(path = "/jxs/message")
public class MessageActivity extends BaseActivity {

    @BindView(R.id.image_comment)
    ImageView mImageComment;
    @BindView(R.id.image_like)
    ImageView mImageLike;
    @BindView(R.id.image_notice)
    ImageView mImageNotice;
    @BindView(R.id.text_comment)
    TextView mTextComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        showBackButton();
        setTitleText("我的消息");
        showTitleLine();

        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
        Badge badge = new QBadgeView(this).bindTarget(mImageComment);
        badge.setBadgeNumber(51);
        badge.setBadgeTextSize(10, true);
        badge.setBadgeGravity(Gravity.END | Gravity.TOP);
        badge.setBadgeBackgroundColor(0xffFF5050);
    }
}
