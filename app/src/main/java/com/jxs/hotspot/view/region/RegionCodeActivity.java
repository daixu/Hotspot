package com.jxs.hotspot.view.region;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.nukc.stateview.StateView;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.RegionCodeAdapter;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.RegionCodeResp;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

@Route(path = "/jxs/region/code")
public class RegionCodeActivity extends BaseActivity implements RegionCodeContract.View {
    @BindView(R.id.recycler_region_code)
    RecyclerView mRecyclerRegionCode;
    private RegionCodeAdapter mAdapter;
    private List<RegionCodeResp.DataBean> mList = new ArrayList<>();

    @Inject
    RegionCodeContract.Presenter mPresenter;
    private StateView mStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_code);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        mStateView = StateView.inject(mRecyclerRegionCode);
        mRecyclerRegionCode.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RegionCodeAdapter(R.layout.item_region_code, mList);
        mRecyclerRegionCode.setAdapter(mAdapter);
        mAdapter.setEnableLoadMore(false);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RegionCodeResp.DataBean dataBean = mList.get(position);
                Intent intent = new Intent();
                intent.putExtra("dataBean", dataBean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initData() {
        mStateView.showLoading();
        mPresenter.takeView(this);
        mPresenter.regionCode();
    }

    @Override
    public void regionCodeSuccess(RegionCodeResp resp) {
        mStateView.showContent();

        List<RegionCodeResp.DataBean> list = resp.data;
        mList.clear();
        mList.addAll(list);
        mAdapter.setNewData(mList);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void regionCodeFailure(String msg) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
