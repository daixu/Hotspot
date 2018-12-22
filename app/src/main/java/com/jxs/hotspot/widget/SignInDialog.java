package com.jxs.hotspot.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jxs.hotspot.R;

public class SignInDialog extends Dialog {

    private SignInOnClickListener mSignInOnClickListener;
    private CloseOnClickListener mCloseOnClickListener;

    private Button mBtnSignIn;
    private ImageView mImageClose;

    public void setSignInOnClickListener(SignInOnClickListener signInOnClickListener) {
        this.mSignInOnClickListener = signInOnClickListener;
    }

    public void setCloseOnClickListener(CloseOnClickListener closeOnClickListener) {
        this.mCloseOnClickListener = closeOnClickListener;
    }

    public SignInDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_in);

        initView();
        initListener();
    }

    private void initView() {
        mBtnSignIn = findViewById(R.id.btn_sign_in);
        mImageClose = findViewById(R.id.image_close);
    }

    private void initListener() {
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSignInOnClickListener != null) {
                    mSignInOnClickListener.onSignInClick();
                }
            }
        });
        mImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCloseOnClickListener != null) {
                    mCloseOnClickListener.onCloseClick();
                }
            }
        });
    }

    public interface SignInOnClickListener {
        void onSignInClick();
    }

    public interface CloseOnClickListener {
        void onCloseClick();
    }
}
