package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.SortResp;

import java.util.List;

public class SortAdapter extends BaseQuickAdapter<SortResp.DataBean, BaseViewHolder> {
    public SortAdapter(int layoutResId, @Nullable List<SortResp.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortResp.DataBean item) {
        String title = item.name;
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.text_title, title);
        }
    }
}
