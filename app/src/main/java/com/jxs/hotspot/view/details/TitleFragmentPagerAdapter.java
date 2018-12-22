package com.jxs.hotspot.view.details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.jxs.hotspot.widget.LazyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TitleFragmentPagerAdapter extends LazyFragmentPagerAdapter {

    /**
     * The m fragment list.
     */
    private List<Fragment> mFragmentList = null;

    private String[] titles;

    /**
     * Instantiates a new ab fragment pager adapter.
     *
     * @param mFragmentManager the m fragment manager
     * @param fragmentList     the fragment list
     */
    public TitleFragmentPagerAdapter(FragmentManager mFragmentManager,
                                     ArrayList<Fragment> fragmentList) {
        super(mFragmentManager);
        mFragmentList = fragmentList;
    }

    /**
     * titles是给TabLayout设置title用的
     *
     * @param mFragmentManager
     * @param fragmentList
     * @param titles
     */
    public TitleFragmentPagerAdapter(FragmentManager mFragmentManager,
                                     List<Fragment> fragmentList, String[] titles) {
        super(mFragmentManager);
        mFragmentList = fragmentList;
        this.titles = titles;
    }

    /**
     * 描述：获取数量.
     *
     * @return the count
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > 0) {
            return titles[position];
        }
        return null;
    }

    /**
     * 描述：获取索引位置的Fragment.
     *
     * @param position the position
     * @return the item
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     */
    @Override
    protected Fragment getItem(ViewGroup container, int position) {
        Fragment fragment;
        if (position < mFragmentList.size()) {
            fragment = mFragmentList.get(position);
        } else {
            fragment = mFragmentList.get(0);
        }
        return fragment;
    }
}