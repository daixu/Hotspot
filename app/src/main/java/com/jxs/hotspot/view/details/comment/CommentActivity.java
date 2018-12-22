package com.jxs.hotspot.view.details.comment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/jxs/comment")
public class CommentActivity extends BaseActivity {

    @BindView(R.id.text_send)
    TextView mTextSend;
    @BindView(R.id.edit_comment)
    EditText mEditComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        addListener();
    }

    private void addListener() {
        mEditComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String comment = mEditComment.getText().toString();
                if (!TextUtils.isEmpty(comment)) {
                    mTextSend.setTextColor(ContextCompat.getColor(CommentActivity.this, R.color.color_7A95F5));
                    mTextSend.setEnabled(true);
                } else {
                    mTextSend.setTextColor(ContextCompat.getColor(CommentActivity.this, R.color.main_body_prompt_color));
                    mTextSend.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.text_cancel, R.id.text_send, R.id.view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_cancel:
                finish();
                break;
            case R.id.text_send:
                break;
            case R.id.view:
                finish();
                break;
            default:
                break;
        }
    }
}
