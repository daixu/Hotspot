package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.RegionCodeResp;

import java.util.List;

public class RegionCodeAdapter extends BaseQuickAdapter<RegionCodeResp.DataBean, BaseViewHolder> {
    public RegionCodeAdapter(int layoutResId, @Nullable List<RegionCodeResp.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RegionCodeResp.DataBean item) {
        helper.setText(R.id.text_title, item.countryName);
        helper.setText(R.id.text_code, item.countryCode);
    }
}
