package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.QuestionListResp;

import java.util.List;

public class QuestionAdapter extends BaseQuickAdapter<QuestionListResp.DataBean.AnswerBean, BaseViewHolder> {
    public QuestionAdapter(int layoutResId, @Nullable List<QuestionListResp.DataBean.AnswerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionListResp.DataBean.AnswerBean item) {
        helper.setText(R.id.text_option, item.option);
        helper.setText(R.id.text_answer, item.title);
        ImageView imageCheck = helper.getView(R.id.image_answer);
        if (item.isClick) {
            imageCheck.setImageResource(R.mipmap.icon_right);
        } else {
            imageCheck.setImageResource(R.drawable.shape_circle_transparent_bg);
        }
        helper.addOnClickListener(R.id.image_answer);
    }
}
