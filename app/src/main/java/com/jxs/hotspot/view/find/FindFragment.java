package com.jxs.hotspot.view.find;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.FindAdapter;
import com.jxs.hotspot.adapter.NetworkImageHolderView;
import com.jxs.hotspot.base.BaseFragment;
import com.jxs.hotspot.bean.BannerResp;
import com.jxs.hotspot.bean.FindListResp;
import com.jxs.hotspot.utils.ToastUtil;
import com.jxs.hotspot.widget.SignInDialog;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class FindFragment extends BaseFragment implements FindContract.View, SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.recycler_find)
    RecyclerView mRecyclerFind;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ConvenientBanner mConvenientBanner;
    private FrameLayout mLayoutTreasure;

    private FindAdapter mAdapter;
    private String mParam1;
    private int pageNum = 1;
    private boolean isEnd = false;

    @Inject
    FindContract.Presenter mPresenter;

    private List<FindListResp.DataBean.ListBean> mList = new ArrayList<>();

    public FindFragment() {
        // Required empty public constructor
    }

    public static FindFragment newInstance(String param1) {
        FindFragment fragment = new FindFragment();
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
        return R.layout.fragment_find;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerFind.setLayoutManager(manager);

        View view = getLayoutInflater().inflate(R.layout.view_find_header, (ViewGroup) mRecyclerFind.getParent(), false);
        View view1 = getLayoutInflater().inflate(R.layout.view_find_header_1, (ViewGroup) mRecyclerFind.getParent(), false);
        mConvenientBanner = view.findViewById(R.id.convenientBanner);
        mLayoutTreasure = view1.findViewById(R.id.layout_treasure);

        mAdapter = new FindAdapter(R.layout.item_find, mList);
        mAdapter.addHeaderView(view);
        mAdapter.addHeaderView(view1);
        mRecyclerFind.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);

        mLayoutTreasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/jxs/detail").navigation();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.takeView(this);
        mPresenter.loadBanner();
        mPresenter.loadFindList();
    }

    @Override
    public void loadBannerSuccess(BannerResp resp) {
        Timber.e("loadBannerSuccess");
        mSwipeRefreshLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerFind.setVisibility(View.VISIBLE);

        if (null != resp.result) {
            loadBanner(resp.result);
        }
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadBannerFailure(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);

        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadFindListSuccess(FindListResp resp) {
        Timber.e("loadFindListSuccess");
        mSwipeRefreshLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerFind.setVisibility(View.VISIBLE);

        TextView textTitle = mLayoutTreasure.findViewById(R.id.text_title);
        textTitle.setText("哈哈哈哈");

        mList.clear();
        if (null != resp.data) {
            if (null != resp.data.list) {
                mList.addAll(resp.data.list);
            }
        }

        mAdapter.setNewData(mList);
        mAdapter.loadMoreComplete();
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void loadFindListFailure(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);

        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.loadBanner();
        pageNum = 1;
        mPresenter.loadFindList();
    }

    private void loadBanner(final List<BannerResp.ResultBean> list) {
        List<String> bannerList = new ArrayList<>();
        for (BannerResp.ResultBean bean : list) {
            bannerList.add(bean.imageAddr);
        }
        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, bannerList).setPageIndicator(new int[]{
                R.mipmap.home_banner2,
                R.mipmap.home_banner1})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position < list.size()) {
                            BannerResp.ResultBean dataBean = list.get(position);
                            if (null != dataBean) {
                                String url = dataBean.linkAddr;
                                ARouter.getInstance().build("/jxs/webView")
                                        .withString("url", url)
                                        .navigation();
                            }
                        }
                    }
                })
                .setCanLoop(bannerList.size() > 1);
        if (bannerList.size() > 1) {
            mConvenientBanner.startTurning(6000);
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    private SignInDialog mSignInDialog;

    private void signIn() {
        if (null != FindFragment.this.getActivity()) {
            mSignInDialog = new SignInDialog(FindFragment.this.getActivity(), R.style.DialogTransparent);
            mSignInDialog.setSignInOnClickListener(new SignInDialog.SignInOnClickListener() {
                @Override
                public void onSignInClick() {
                    ToastUtil.showTip(FindFragment.this.getActivity(), "哈哈哈哈哈");
                }
            });

            mSignInDialog.setCloseOnClickListener(new SignInDialog.CloseOnClickListener() {
                @Override
                public void onCloseClick() {
                    mSignInDialog.dismiss();
                }
            });
            mSignInDialog.show();
        }
    }

    @OnClick({R.id.image_sign_in, R.id.image_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_sign_in: {
                signIn();
            }
            break;
            case R.id.image_search: {
                ARouter.getInstance().build("/jxs/search").navigation();
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (null != mSignInDialog) {
            mSignInDialog.dismiss();
            mSignInDialog = null;
        }
    }
}
