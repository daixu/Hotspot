package com.jxs.hotspot.view.details;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DetailActivity detailActivity();

    @ActivityScoped
    @Binds
    abstract DetailContract.Presenter detailPresenter(DetailPresenter presenter);
}
