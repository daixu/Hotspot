package com.jxs.hotspot.view.pwd;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.MessageEvent;
import com.jxs.hotspot.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jxs.hotspot.common.Constant.EventType.EVENT_RESET_LOGIN_PWD_TYPE;

@Route(path = "/jxs/forget/pwd")
public class ForgetPwdActivity extends BaseActivity {
    @BindView(R.id.edit_account)
    EditText mEditAccount;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);

        showBackButton();
        setTitleText("忘记密码");
        showTitleLine();
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        String account = mEditAccount.getText().toString();
        if (!TextUtils.isEmpty(account)) {
            showSafetyPopWindow(account);
            hideKeyboard();
        } else {
            ToastUtil.showTip(this, "账号输入不能为空");
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (null != imm && imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 弹出popupWindow
     */
    private void showSafetyPopWindow(String account) {
        View mPopupView = View.inflate(this, R.layout.pop_safety_layout, null);
        mPopupWindow = new PopupWindow(mPopupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        View view = mPopupView.findViewById(R.id.view);
        TextView textTitle = mPopupView.findViewById(R.id.text_title);
        ImageView imageClose = mPopupView.findViewById(R.id.image_close);
        Button btnNext = mPopupView.findViewById(R.id.btn_next);
        final EditText editVerCode = mPopupView.findViewById(R.id.edit_ver_code);
        textTitle.setText(account);
        if (account.contains("@")) {
            editVerCode.setHint("请输入邮箱验证码");
        } else {
            editVerCode.setHint("请输入手机验证码");
        }

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verCode = editVerCode.getText().toString();
                Timber.e("verCode= " + verCode);
                hideKeyboard();
                ARouter.getInstance().build("/jxs/reset/pwd").navigation();
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        });

        // 设置背景图片， 必须设置，不然动画没作用
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);

        // 设置点击popupWindow外屏幕其它地方消失
        mPopupWindow.setOutsideTouchable(true);

        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
        TranslateAnimation mAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setDuration(200);

        // 在点击之后设置popupWindow的销毁
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mPopupWindow.showAtLocation(findViewById(R.id.layout_content), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mPopupView.startAnimation(mAnimation);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshCompletedEvent(MessageEvent event) {
        if (event.type == EVENT_RESET_LOGIN_PWD_TYPE) {
            String message = event.message;
            if (!TextUtils.isEmpty(message) && message.equals("finish")) {
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus(ForgetPwdActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(ForgetPwdActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
}
