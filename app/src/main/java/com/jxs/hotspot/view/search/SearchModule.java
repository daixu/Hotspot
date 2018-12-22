package com.jxs.hotspot.view.search;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SearchModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SearchActivity searchActivity();

    @ActivityScoped
    @Binds
    abstract SearchContract.Presenter searchPresenter(SearchPresenter presenter);
}
