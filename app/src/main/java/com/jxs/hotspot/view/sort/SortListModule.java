package com.jxs.hotspot.view.sort;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SortListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SortListActivity sortListActivity();

    @ActivityScoped
    @Binds
    abstract SortListContract.Presenter sortListPresenter(SortListPresenter presenter);
}
