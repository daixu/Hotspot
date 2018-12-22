package com.jxs.hotspot.view.sort;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.nukc.stateview.StateView;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.SortListAdapter;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.SortListResp;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

@Route(path = "/jxs/sort/list")
public class SortListActivity extends BaseActivity implements SortListContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_sort_list)
    RecyclerView mRecyclerSortList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private StateView mStateView;

    private SortListAdapter mAdapter;

    private List<SortListResp.DataBean> mList = new ArrayList<>();

    @Inject
    SortListContract.Presenter mPresenter;

    @Autowired
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);

        initView();
        initData();
        Timber.e("id= " + id);
    }

    private void initData() {
        mStateView.showLoading();
        mPresenter.takeView(this);
        mPresenter.loadSortList();
    }

    private void initView() {
        showBackButton();
        setTitleText("行情");
        showTitleLine();

        mStateView = StateView.inject(mSwipeRefreshLayout);

        ImageButton imageButton = findViewById(R.id.img_btn_title_right);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setImageResource(R.mipmap.icon_menu);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerSortList.setLayoutManager(manager);

        mAdapter = new SortListAdapter(R.layout.item_sort_list, mList);
        mRecyclerSortList.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void loadSortListSuccess(SortListResp resp) {
        mSwipeRefreshLayout.setRefreshing(false);
        mStateView.showContent();
        mRecyclerSortList.setVisibility(View.VISIBLE);

        mList.clear();
        if (null != resp.data) {
            mList.addAll(resp.data);
        }

        mAdapter.setNewData(mList);
        mAdapter.loadMoreComplete();
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadSortListFailure(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        mStateView.showRetry();

        mSwipeRefreshLayout.setEnabled(true);
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
