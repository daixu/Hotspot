package com.jxs.hotspot.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.bean.CommentListResp;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<CommentListResp.DataBean, BaseViewHolder> {

    public CommentAdapter(@LayoutRes int layoutResId, @Nullable List<CommentListResp.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentListResp.DataBean dataBean) {

    }
}