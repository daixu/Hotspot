package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.SearchHotWordResp;

import java.util.List;

public class SearchHotWordAdapter extends BaseQuickAdapter<SearchHotWordResp.DataBean, BaseViewHolder> {

    public SearchHotWordAdapter(int layoutResId, @Nullable List<SearchHotWordResp.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHotWordResp.DataBean item) {
        helper.setText(R.id.text_name, item.name);
    }
}
