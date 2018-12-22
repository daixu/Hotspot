package com.jxs.hotspot.view.details.recommend;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecommendModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RecommendFragment recommendFragment();

    @ActivityScoped
    @Binds
    abstract RecommendContract.Presenter recommendContract(RecommendPresenter presenter);
}
