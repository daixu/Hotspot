package com.jxs.hotspot.view.sort;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SortModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SortFragment sortFragment();

    @ActivityScoped
    @Binds
    abstract SortContract.Presenter sortPresenter(SortPresenter presenter);
}
