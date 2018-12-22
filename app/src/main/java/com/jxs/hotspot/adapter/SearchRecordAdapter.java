package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;

import java.util.List;

public class SearchRecordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchRecordAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text_name, item);
    }
}
