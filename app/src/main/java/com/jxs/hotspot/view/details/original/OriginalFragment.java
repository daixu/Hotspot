package com.jxs.hotspot.view.details.original;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.OriginalListAdapter;
import com.jxs.hotspot.base.BaseFragment;
import com.jxs.hotspot.bean.OriginalListResp;
import com.jxs.hotspot.widget.LazyFragmentPagerAdapter;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class OriginalFragment extends BaseFragment implements LazyFragmentPagerAdapter.Laziable, OriginalContract.View {
    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.recycler_original)
    RecyclerView mRecyclerOriginal;

    private String mParam1;

    @Inject
    OriginalContract.Presenter mPresenter;

    private OriginalListAdapter mAdapter;

    private List<OriginalListResp.DataBean> mList = new ArrayList<>();

    public OriginalFragment() {
        // Required empty public constructor
    }

    public static OriginalFragment newInstance(String param1) {
        OriginalFragment fragment = new OriginalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_original;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerOriginal.setLayoutManager(manager);

        mAdapter = new OriginalListAdapter(R.layout.item_original, mList);
        mRecyclerOriginal.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.takeView(this);
        mPresenter.getOriginalList();
    }

    @Override
    public void getOriginalListSuccess(OriginalListResp resp) {
        Timber.e("getOriginalListSuccess");
        mList.clear();
        if (null != resp.data) {
            mList.addAll(resp.data);
        }

        mAdapter.setNewData(mList);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void getOriginalListFailure(String msg) {
        Timber.e("getOriginalListFailure msg=" + msg);
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
