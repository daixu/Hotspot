package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.bean.SearchResp;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<SearchResp.DataBean, BaseViewHolder> {
    public SearchAdapter(int layoutResId, @Nullable List<SearchResp.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResp.DataBean item) {

    }
}
