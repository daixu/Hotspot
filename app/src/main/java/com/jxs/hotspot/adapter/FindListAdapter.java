package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.FindListResp;

import java.util.List;

public class FindListAdapter extends BaseQuickAdapter<FindListResp.DataBean.ListBean.FindListBean, BaseViewHolder> {
    public FindListAdapter(int layoutResId, @Nullable List<FindListResp.DataBean.ListBean.FindListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindListResp.DataBean.ListBean.FindListBean item) {
        int type = item.type;
        FrameLayout layoutTreasure = helper.getView(R.id.layout_treasure);
        ConstraintLayout layoutNormal = helper.getView(R.id.layout_normal);
        // 头条
        if (type == 1) {
            layoutTreasure.setVisibility(View.VISIBLE);
            layoutNormal.setVisibility(View.GONE);
        } else {
            // 普通列表
            layoutNormal.setVisibility(View.VISIBLE);
            layoutTreasure.setVisibility(View.GONE);
        }
        helper.setText(R.id.text_title, item.title);
        helper.setText(R.id.text_title_normal, item.title);
    }
}
