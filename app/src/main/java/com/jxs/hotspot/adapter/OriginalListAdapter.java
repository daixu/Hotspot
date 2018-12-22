package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.OriginalListResp;

import java.util.List;

public class OriginalListAdapter extends BaseQuickAdapter<OriginalListResp.DataBean, BaseViewHolder> {
    public OriginalListAdapter(int layoutResId, @Nullable List<OriginalListResp.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OriginalListResp.DataBean item) {
        helper.setText(R.id.text_title, item.title);
    }
}
