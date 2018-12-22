package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.QuestionListResp;

import java.util.List;

public class QuestionListAdapter extends BaseQuickAdapter<QuestionListResp.DataBean.AnswerBean, BaseViewHolder> {
    public QuestionListAdapter(int layoutResId, @Nullable List<QuestionListResp.DataBean.AnswerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionListResp.DataBean.AnswerBean item) {
        helper.setText(R.id.text_option, item.option);
        helper.setText(R.id.text_answer, item.title);
    }
}
