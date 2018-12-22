package com.jxs.hotspot.di;

import com.jxs.hotspot.view.details.DetailActivity;
import com.jxs.hotspot.view.details.DetailModule;
import com.jxs.hotspot.view.details.PersonalDetailsActivity;
import com.jxs.hotspot.view.details.original.OriginalModule;
import com.jxs.hotspot.view.details.question.QuestionActivity;
import com.jxs.hotspot.view.details.question.QuestionModule;
import com.jxs.hotspot.view.find.FindModule;
import com.jxs.hotspot.view.login.LoginActivity;
import com.jxs.hotspot.view.login.LoginModule;
import com.jxs.hotspot.view.main.MainActivity;
import com.jxs.hotspot.view.message.comment.MyCommentActivity;
import com.jxs.hotspot.view.message.comment.MyCommentModule;
import com.jxs.hotspot.view.region.RegionCodeActivity;
import com.jxs.hotspot.view.region.RegionCodeModule;
import com.jxs.hotspot.view.register.RegisterActivity;
import com.jxs.hotspot.view.register.RegisterModule;
import com.jxs.hotspot.view.search.SearchActivity;
import com.jxs.hotspot.view.search.SearchModule;
import com.jxs.hotspot.view.sort.SortListActivity;
import com.jxs.hotspot.view.sort.SortListModule;
import com.jxs.hotspot.view.sort.SortModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {FindModule.class, SortModule.class})
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DetailModule.class)
    abstract DetailActivity detailActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = QuestionModule.class)
    abstract QuestionActivity questionActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchActivity searchActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SortListModule.class)
    abstract SortListActivity sortListActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {OriginalModule.class})
    abstract PersonalDetailsActivity personalDetailsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = RegionCodeModule.class)
    abstract RegionCodeActivity regionCodeActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = RegisterModule.class)
    abstract RegisterActivity registerActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MyCommentModule.class)
    abstract MyCommentActivity myCommentActivity();
}
