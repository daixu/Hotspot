package com.jxs.hotspot.view.region;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RegionCodeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RegionCodeActivity regionCodeActivity();

    @ActivityScoped
    @Binds
    abstract RegionCodeContract.Presenter regionCodePresenter(RegionCodePresenter presenter);
}
