package com.jxs.hotspot.view.sort;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.nukc.stateview.StateView;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.SortAdapter;
import com.jxs.hotspot.base.BaseFragment;
import com.jxs.hotspot.bean.SortResp;
import com.jxs.hotspot.widget.LazyFragmentPagerAdapter;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class SortFragment extends BaseFragment implements SortContract.View, SwipeRefreshLayout.OnRefreshListener, LazyFragmentPagerAdapter.Laziable {
    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.recycler_sort)
    RecyclerView mRecyclerSort;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SortAdapter mAdapter;
    private String mParam1;
    private int pageNum = 1;
    private boolean isEnd = false;

    private StateView mStateView;

    @Inject
    SortContract.Presenter mPresenter;

    private List<SortResp.DataBean> mList = new ArrayList<>();

    public SortFragment() {
    }

    public static SortFragment newInstance(String param1) {
        SortFragment fragment = new SortFragment();
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
    public void onResume() {
        super.onResume();

        mPresenter.takeView(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        GridLayoutManager manager = new GridLayoutManager(this.getActivity(), 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerSort.setLayoutManager(manager);

        mStateView = StateView.inject(mSwipeRefreshLayout);

        mAdapter = new SortAdapter(R.layout.item_sort, mList);
        mRecyclerSort.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SortResp.DataBean dataBean = mList.get(position);
                ARouter.getInstance()
                        .build("/jxs/sort/list")
                        .withString("id", dataBean.id)
                        .navigation();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.takeView(this);
        mStateView.showLoading();
        mPresenter.loadSort();
    }

    @Override
    public void loadSortSuccess(SortResp resp) {
        Timber.e("SortFragment loadSortSuccess");
        mSwipeRefreshLayout.setRefreshing(false);
        mStateView.showContent();
        mRecyclerSort.setVisibility(View.VISIBLE);

        mList.clear();
        if (null != resp.data) {
            mList.addAll(resp.data);
        }

        mAdapter.setNewData(mList);
        mAdapter.loadMoreComplete();
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadSortFailure(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        mStateView.showRetry();

        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        pageNum = 1;
        mPresenter.loadSort();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

}
