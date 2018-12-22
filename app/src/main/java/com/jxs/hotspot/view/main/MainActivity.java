package com.jxs.hotspot.view.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.utils.ToastUtil;
import com.jxs.hotspot.view.find.FindFragment;
import com.jxs.hotspot.view.me.MeFragment;
import com.jxs.hotspot.view.sort.SortFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector, BottomNavigationBar.OnTabSelectedListener {

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @BindView(R.id.bottomNavigationBar)
    BottomNavigationBar mBottomNavigationBar;

    private FindFragment mFindFragment;
    private SortFragment mSortFragment;
    private MeFragment meFragment;

    private FragmentManager fm;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        fm = getSupportFragmentManager();
        initBottomNavigationBar();

        setDefaultFragment();
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        if (mFindFragment == null) {
            mFindFragment = FindFragment.newInstance(getString(R.string.tab_find));
        }
        showFragment(mFindFragment);
    }

    private void initBottomNavigationBar() {
        mBottomNavigationBar = findViewById(R.id.bottomNavigationBar);
        mBottomNavigationBar.clearAll();

        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.icon_find_click, getString(R.string.tab_find)).setActiveColorResource(R.color.tab_click_color)
                        .setInActiveColorResource(R.color.main_body_prompt_color).setInactiveIconResource(R.mipmap.icon_find_un_click))
                .addItem(new BottomNavigationItem(R.mipmap.icon_sort_click, getString(R.string.tab_sort)).setActiveColorResource(R.color.tab_click_color)
                        .setInActiveColorResource(R.color.main_body_prompt_color).setInactiveIconResource(R.mipmap.icon_sort_un_click))
                .addItem(new BottomNavigationItem(R.mipmap.icon_me_click, getString(R.string.tab_me)).setActiveColorResource(R.color.tab_click_color)
                        .setInActiveColorResource(R.color.main_body_prompt_color).setInactiveIconResource(R.mipmap.icon_me_un_click))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                if (mFindFragment == null) {
                    mFindFragment = FindFragment.newInstance(getString(R.string.tab_find));
                }
                showFragment(mFindFragment);
                break;
            case 1:
                if (mSortFragment == null) {
                    mSortFragment = SortFragment.newInstance(getString(R.string.tab_sort));
                }
                showFragment(mSortFragment);
                break;
            case 2:
                if (meFragment == null) {
                    meFragment = MeFragment.newInstance(getString(R.string.tab_me));
                }
                showFragment(meFragment);
                break;
            default:
                break;
        }
        // 事务提交
//        transaction.commit();
    }

    private void showFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            @SuppressLint("RestrictedApi") List<Fragment> fragList = fm.getFragments();
            FragmentTransaction transaction = fm.beginTransaction();
            if (fragList != null && fragList.contains(fragment)) {
                for (Fragment frag : fragList) {
                    if (frag.equals(fragment) && frag.isHidden()) {
                        transaction.show(frag);
                    } else {
                        transaction.hide(frag);
                    }
                }
                transaction.commitAllowingStateLoss();
            } else {
                if (fragList != null && fragList.size() > 0) {
                    for (Fragment frag : fragList) {
                        transaction.hide(frag);
                    }
                }
                transaction.add(R.id.bottom_nav_content, fragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showTip(this, R.string.app_exit_prompt);
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
