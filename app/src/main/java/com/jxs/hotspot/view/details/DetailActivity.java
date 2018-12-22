package com.jxs.hotspot.view.details;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.nukc.stateview.StateView;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.CommentAdapter;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.BaseResp;
import com.jxs.hotspot.bean.CommentListResp;
import com.jxs.hotspot.bean.FindDetailResp;
import com.jxs.hotspot.bean.RewardInfoResp;
import com.jxs.hotspot.bean.UserDetailCountResp;
import com.jxs.hotspot.utils.ToastUtil;
import com.jxs.hotspot.widget.EditInputFilter;
import com.jxs.hotspot.widget.powerfulrecyclerview.PowerfulRecyclerView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import dagger.android.AndroidInjection;
import timber.log.Timber;

@Route(path = "/jxs/detail")
public class DetailActivity extends BaseActivity implements DetailContract.View {

    protected DetailHeaderView mHeaderView;
    @BindView(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;
    private CommentAdapter mCommentAdapter;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    private List<CommentListResp.DataBean> mCommentList = new ArrayList<>();

    @Inject
    DetailContract.Presenter mPresenter;

    private StateView mStateView;

    private int pageNum = 1;
    private boolean isEnd;

    private PopupWindow mPopupWindow;
    private View mPopupView;
    private TranslateAnimation mAnimation;

    private PopupWindow mSharePopupWindow;
    private View mSharePopupView;
    private TranslateAnimation mShareAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    private void initData() {
        mStateView.showLoading();
        mPresenter.takeView(this);
        mPresenter.getDetails();
        mPresenter.getComment();
        mPresenter.getUserDetailCount();
    }

    private void initView() {
        showBackButton();
        setTitleText("资讯详情");
        showTitleLine();

        mStateView = StateView.inject(mFlContent);

        ImageButton imageButton = findViewById(R.id.img_btn_title_right);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setImageResource(R.mipmap.icon_share_1);
    }

    private void initListener() {
        mCommentAdapter = new CommentAdapter(R.layout.item_comment, mCommentList);
        mRvComment.setAdapter(mCommentAdapter);
        mHeaderView = new DetailHeaderView(this);

        mCommentAdapter.addHeaderView(mHeaderView);

        mCommentAdapter.setEnableLoadMore(true);
        // mCommentAdapter.setOnLoadMoreListener(this, mRvComment);

        // mCommentAdapter.setEmptyView(R.layout.pager_no_comment);
        mCommentAdapter.setHeaderAndEmpty(true);
    }

    @Override
    public void getDetailsSuccess(FindDetailResp.DataBean dataBean) {
        mHeaderView.setDetail(dataBean, new DetailHeaderView.LoadWebListener() {
            @Override
            public void onLoadFinished() {
                //加载完成后，显示内容布局
                mStateView.showContent();
            }
        });
    }

    @Override
    public void getDetailsFailure(String msg) {
        Timber.e("getDetailsFailure msg=" + msg);
    }

    @Override
    public void getCommentSuccess(CommentListResp resp) {
        if (null != resp.data) {
            List<CommentListResp.DataBean> list = resp.data;
            if (pageNum == 1) {
                mCommentList.clear();
            }
            mCommentList.addAll(list);
//            if (pageNum >= resp.data.pages) {
//                isEnd = true;
//            } else {
//                isEnd = false;
//                pageNum += 1;
//            }
            mCommentAdapter.setNewData(mCommentList);
            mCommentAdapter.loadMoreComplete();
        }
    }

    @Override
    public void getCommentFailure(String msg) {
        Timber.e("getCommentFailure");
    }

    @Override
    public void getRewardInfoSuccess(RewardInfoResp resp) {
        if (null != resp) {
            showRewardPopWindow(resp);
        }
    }

    @Override
    public void getRewardInfoFailure(String msg) {
        Timber.e("getRewardInfoFailure msg=" + msg);
    }

    @Override
    public void rewardSuccess(BaseResp resp) {
        ToastUtil.showTip(this, resp.msgZh);
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void rewardFailure(String msg) {
        Timber.e("rewardFailure msg=" + msg);
    }

    @Override
    public void getUserDetailCountSuccess(UserDetailCountResp resp) {
        mHeaderView.setDetailCount(resp);
    }

    @Override
    public void getUserDetailCountFailure(String msg) {
        Timber.e("getUserDetailCountFailure msg=" + msg);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @OnClick({R.id.text_comment, R.id.image_collect, R.id.image_reward, R.id.image_share, R.id.img_btn_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_comment:
                ARouter.getInstance().build("/jxs/comment").navigation();
                break;
            case R.id.image_collect:
                break;
            case R.id.image_reward: {
                mPresenter.getRewardInfo();
            }
            break;
            case R.id.image_share:
                break;
            case R.id.img_btn_title_right: {
                showSharePopWindow();
            }
            break;
            default:
                break;
        }
    }

    /**
     * 弹出popupWindow
     */
    private void showRewardPopWindow(RewardInfoResp resp) {
        if (mPopupWindow == null) {
            mPopupView = View.inflate(this, R.layout.pop_reward_layout, null);
            mPopupWindow = new PopupWindow(mPopupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);

            View view = mPopupView.findViewById(R.id.view);
            ImageView imageClose = mPopupView.findViewById(R.id.image_close);
            Button btnReward = mPopupView.findViewById(R.id.btn_reward);
            TextView textUnit = mPopupView.findViewById(R.id.text_unit);
            final EditText editMoney = mPopupView.findViewById(R.id.edit_money);

            textUnit.setText(resp.data.unit);
            InputFilter[] filters = {new EditInputFilter(this)};
            editMoney.setFilters(filters);
            imageClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                }
            });

            btnReward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String money = editMoney.getText().toString();
                    mPresenter.reward(money);
                }
            });

            // 设置背景图片， 必须设置，不然动画没作用
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setFocusable(true);

            // 设置点击popupWindow外屏幕其它地方消失
            mPopupWindow.setOutsideTouchable(true);

            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            mAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            mAnimation.setInterpolator(new AccelerateInterpolator());
            mAnimation.setDuration(200);
        }

        // 在点击之后设置popupWindow的销毁
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mPopupWindow.showAtLocation(findViewById(R.id.layout_content), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mPopupView.startAnimation(mAnimation);
    }

    /**
     * 弹出popupWindow
     */
    private void showSharePopWindow() {
        if (mSharePopupWindow == null) {
            mSharePopupView = View.inflate(this, R.layout.pop_share_layout, null);
            mSharePopupWindow = new PopupWindow(mSharePopupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);

            View view = mSharePopupView.findViewById(R.id.view);
            TextView textCancel = mSharePopupView.findViewById(R.id.text_cancel);
            LinearLayout layoutWeChat = mSharePopupView.findViewById(R.id.layout_wechat);
            LinearLayout layoutWeChatCircle = mSharePopupView.findViewById(R.id.layout_wechat_circle);
            LinearLayout layoutQq = mSharePopupView.findViewById(R.id.layout_qq);
            LinearLayout layoutWeibo = mSharePopupView.findViewById(R.id.layout_weibo);
            textCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSharePopupWindow.dismiss();
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSharePopupWindow.dismiss();
                }
            });

            layoutWeChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            layoutWeChatCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            layoutQq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            layoutWeibo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            // 设置背景图片， 必须设置，不然动画没作用
            mSharePopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mSharePopupWindow.setFocusable(true);

            // 设置点击popupWindow外屏幕其它地方消失
            mSharePopupWindow.setOutsideTouchable(true);

            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            mShareAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            mShareAnimation.setInterpolator(new AccelerateInterpolator());
            mShareAnimation.setDuration(200);
        }

        // 在点击之后设置popupWindow的销毁
        if (mSharePopupWindow.isShowing()) {
            mSharePopupWindow.dismiss();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mSharePopupWindow.showAtLocation(findViewById(R.id.layout_content), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mSharePopupView.startAnimation(mShareAnimation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
        }
        if (null != mSharePopupWindow) {
            mSharePopupWindow.dismiss();
        }
    }
}
