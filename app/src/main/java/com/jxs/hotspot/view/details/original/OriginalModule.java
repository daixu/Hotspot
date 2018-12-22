package com.jxs.hotspot.view.details.original;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OriginalModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract OriginalFragment originalFragment();

    @ActivityScoped
    @Binds
    abstract OriginalContract.Presenter originalPresenter(OriginalPresenter presenter);
}
