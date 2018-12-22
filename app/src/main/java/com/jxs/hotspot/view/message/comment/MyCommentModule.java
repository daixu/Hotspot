package com.jxs.hotspot.view.message.comment;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyCommentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MyCommentActivity myCommentActivity();

    @ActivityScoped
    @Binds
    abstract MyCommentContract.Presenter myCommentPresenter(MyCommentPresenter presenter);
}
