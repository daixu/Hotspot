package com.jxs.hotspot.view.register;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RegisterModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RegisterActivity registerActivity();

    @ActivityScoped
    @Binds
    abstract RegisterContract.Presenter registerPresenter(RegisterPresenter presenter);
}
