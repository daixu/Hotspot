package com.jxs.hotspot.view.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseFragment;
import com.jxs.hotspot.widget.LazyFragmentPagerAdapter;
import com.jxs.hotspot.widget.pulltozoomview.PullToZoomScrollViewEx;

import butterknife.BindView;

public class MeDevFragment extends BaseFragment implements LazyFragmentPagerAdapter.Laziable {
    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.scroll_view)
    PullToZoomScrollViewEx mScrollView;

    private String mParam1;

    public MeDevFragment() {
        // Required empty public constructor
    }

    public static MeDevFragment newInstance(String param1) {
        MeDevFragment fragment = new MeDevFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
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
        return R.layout.fragment_me_dev;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        loadViewForCode();
    }

    @Override
    protected void initData() {

    }

    private void loadViewForCode() {
        View headView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this.getActivity()).inflate(R.layout.profile_content_view, null, false);
        mScrollView.setHeaderView(headView);
        mScrollView.setZoomView(zoomView);
        mScrollView.setScrollContentView(contentView);
    }
}
