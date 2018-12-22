package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.SortListResp;

import java.util.List;

public class SortListAdapter extends BaseQuickAdapter<SortListResp.DataBean, BaseViewHolder> {
    public SortListAdapter(int layoutResId, @Nullable List<SortListResp.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortListResp.DataBean item) {
        String title = item.title;
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.text_title, title);
        }
    }
}
