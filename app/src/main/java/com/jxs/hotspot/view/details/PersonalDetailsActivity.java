package com.jxs.hotspot.view.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.UserDetailCountResp;
import com.jxs.hotspot.view.details.like.LikeFragment;
import com.jxs.hotspot.view.details.original.OriginalFragment;
import com.jxs.hotspot.view.details.recommend.RecommendFragment;
import com.jxs.hotspot.view.details.reward.RewardFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

@Route(path = "/jxs/personal/detail")
public class PersonalDetailsActivity extends BaseActivity implements HasSupportFragmentInjector {
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.text_original)
    TextView mTextOriginal;
    @BindView(R.id.text_recommend)
    TextView mTextRecommend;
    @BindView(R.id.text_like)
    TextView mTextLike;
    @BindView(R.id.text_reward)
    TextView mTextReward;

    private String[] tabNames = new String[4];

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Autowired
    public UserDetailCountResp resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    private void initView() {
        if (null != resp && null != resp.data) {
            tabNames[0] = "原创(" + resp.data.original + ")";
            tabNames[1] = "推荐(" + resp.data.recommend + ")";
            tabNames[2] = "喜欢(" + resp.data.like + ")";
            tabNames[3] = "打赏(" + resp.data.reward + ")";
        } else {
            tabNames[0] = "原创";
            tabNames[1] = "推荐";
            tabNames[2] = "喜欢";
            tabNames[3] = "打赏";
        }

        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new OriginalFragment();
                        break;
                    case 1:
                        fragment = new RecommendFragment();
                        break;
                    case 2:
                        fragment = new LikeFragment();
                        break;
                    case 3:
                        fragment = new RewardFragment();
                        break;
                    default:
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return tabNames.length;
            }
        });
        mViewpager.setCurrentItem(0);
    }

    private void initListener() {
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTextOriginal.setTextColor(Color.WHITE);
                        mTextOriginal.setBackgroundResource(R.drawable.shape_detail_title_select_left_bg);
                        mTextRecommend.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextRecommend.setBackgroundColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.btn_detail_bg_1));
                        mTextLike.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextLike.setBackgroundColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.btn_detail_bg_1));
                        mTextReward.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextReward.setBackgroundResource(R.drawable.shape_detail_title_right_bg);
                        break;
                    case 1:
                        mTextOriginal.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextOriginal.setBackgroundResource(R.drawable.shape_detail_title_left_bg);
                        mTextRecommend.setTextColor(Color.WHITE);
                        mTextRecommend.setBackgroundColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.btn_detail_bg));
                        mTextLike.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextLike.setBackgroundColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.btn_detail_bg_1));
                        mTextReward.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextReward.setBackgroundResource(R.drawable.shape_detail_title_right_bg);
                        break;
                    case 2:
                        mTextOriginal.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextOriginal.setBackgroundResource(R.drawable.shape_detail_title_left_bg);
                        mTextRecommend.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextRecommend.setBackgroundColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.btn_detail_bg_1));
                        mTextLike.setTextColor(Color.WHITE);
                        mTextLike.setBackgroundColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.btn_detail_bg));
                        mTextReward.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextReward.setBackgroundResource(R.drawable.shape_detail_title_right_bg);
                        break;
                    case 3:
                        mTextOriginal.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextOriginal.setBackgroundResource(R.drawable.shape_detail_title_left_bg);
                        mTextRecommend.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextRecommend.setBackgroundColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.btn_detail_bg_1));
                        mTextLike.setTextColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.text_detail_color));
                        mTextLike.setBackgroundColor(ContextCompat.getColor(PersonalDetailsActivity.this, R.color.btn_detail_bg_1));
                        mTextReward.setTextColor(Color.WHITE);
                        mTextReward.setBackgroundResource(R.drawable.shape_detail_title_select_right_bg);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.text_original, R.id.text_recommend, R.id.text_like, R.id.text_reward})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_original:
                mViewpager.setCurrentItem(0);
                break;
            case R.id.text_recommend:
                mViewpager.setCurrentItem(1);
                break;
            case R.id.text_like:
                mViewpager.setCurrentItem(2);
                break;
            case R.id.text_reward:
                mViewpager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }
}
