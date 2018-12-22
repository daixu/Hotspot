package com.jxs.hotspot.view.message.comment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.nukc.stateview.StateView;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.MyCommentAdapter;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.BaseResp;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MyCommentActivity extends BaseActivity implements MyCommentContract.View {

    @BindView(R.id.recycler_my_comment)
    RecyclerView mRecyclerMyComment;
    private MyCommentAdapter mAdapter;

    @Inject
    MyCommentContract.Presenter mPresenter;

    private StateView mStateView;

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        ButterKnife.bind(this);

        showBackButton();
        setTitleText("评论/回复");
        showTitleLine();

        initView();
        initData();
    }

    private void initView() {
        mStateView = StateView.inject(mRecyclerMyComment);

        mRecyclerMyComment.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyCommentAdapter(R.layout.item_my_comment, mList);
        mRecyclerMyComment.setAdapter(mAdapter);
        mAdapter.setEnableLoadMore(false);
    }

    private void initData() {
        mStateView.showLoading();
        mPresenter.takeView(this);
        mPresenter.getMyCommentList();
    }

    @Override
    public void getMyCommentListSuccess(BaseResp resp) {
        mStateView.showContent();
    }

    @Override
    public void getMyCommentListFailure(String msg) {

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
