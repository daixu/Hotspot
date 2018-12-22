package com.jxs.hotspot.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.QuestionAdapter;
import com.jxs.hotspot.bean.QuestionListResp;

import java.util.ArrayList;
import java.util.List;

public class QuestionDialog extends Dialog {
    private ExitOnClickListener mExitOnClickListener;
    private OnItemClickListener mOnItemClickListener;

    private Button mBtnExit;
    private QuestionAdapter mQuestionAdapter;

    private List<QuestionListResp.DataBean> mList = new ArrayList<>();

    private Context mContext;

    public QuestionDialog(@NonNull Context context, int themeResId, List<QuestionListResp.DataBean> list) {
        super(context, themeResId);
        mContext = context;
        mList.clear();
        mList.addAll(list);
    }

    public void setExitOnClickListener(ExitOnClickListener exitOnClickListener) {
        this.mExitOnClickListener = exitOnClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_question);
        initView();
        initListener();
    }

    private void initView() {
        mBtnExit = findViewById(R.id.btn_exit);
        RecyclerView recyclerAnswer = findViewById(R.id.recycler_answer);

        recyclerAnswer.setLayoutManager(new LinearLayoutManager(mContext));
        mQuestionAdapter = new QuestionAdapter(R.layout.item_question, null);
        recyclerAnswer.setAdapter(mQuestionAdapter);
        mQuestionAdapter.setEnableLoadMore(false);
    }

    private void initListener() {
        mBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExitOnClickListener != null) {
                    mExitOnClickListener.onExitClick();
                }
            }
        });

        mQuestionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(adapter, view, position);
                }
            }
        });
    }

    public interface ExitOnClickListener {
        void onExitClick();
    }

    public interface OnItemClickListener {
        void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }
}
