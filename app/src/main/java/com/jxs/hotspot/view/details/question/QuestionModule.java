package com.jxs.hotspot.view.details.question;

import com.jxs.hotspot.di.ActivityScoped;
import com.jxs.hotspot.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class QuestionModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract QuestionActivity questionActivity();

    @ActivityScoped
    @Binds
    abstract QuestionContract.Presenter questionPresenter(QuestionPresenter presenter);
}
