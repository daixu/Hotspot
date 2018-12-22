package com.jxs.hotspot.view.find;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FindModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FindFragment findFragment();

    @ActivityScoped
    @Binds
    abstract FindContract.Presenter findPresenter(FindPresenter presenter);
}
