package com.jxs.hotspot.view.login;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @Binds
    abstract LoginContract.Presenter loginPresenter(LoginPresenter presenter);
}
