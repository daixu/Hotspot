package com.jxs.hotspot.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment {

    private View parentView;
    private boolean isCreated;
    protected boolean isVisible;
    protected boolean isLazyLoad;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, parentView);
        initView(savedInstanceState);
        initData();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isCreated = true;
        onVisible();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        if (isVisible && !isLazyLoad && isCreated) {
            isLazyLoad = true;
            lazyLoad();
        }
    }

    protected void lazyLoad() {
    }

    protected void onInvisible() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract
    @LayoutRes
    int getLayoutResId();

    protected abstract void initView(@Nullable Bundle savedInstanceState);

    protected abstract void initData();

    protected <T extends View> T findViewById(int resId) {
        return parentView.findViewById(resId);
    }

}