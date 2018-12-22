package com.jxs.hotspot.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.FindListResp;

import java.util.List;

public class FindAdapter extends BaseQuickAdapter<FindListResp.DataBean.ListBean, BaseViewHolder> {
    public FindAdapter(int layoutResId, @Nullable List<FindListResp.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindListResp.DataBean.ListBean item) {
        String title = item.name;
        if (!TextUtils.isEmpty(title)) {
            helper.setVisible(R.id.text_title, true);
            helper.setText(R.id.text_title, title);
        } else {
            helper.setVisible(R.id.text_title, false);
        }

        RecyclerView recyclerView = helper.getView(R.id.recycler_find_list);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        final List<FindListResp.DataBean.ListBean.FindListBean> list = item.findList;
        FindListAdapter adapter = new FindListAdapter(R.layout.item_find_list, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build("/jxs/detail").navigation();
            }
        });
    }
}
