package com.jxs.hotspot.view.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseFragment;
import com.jxs.hotspot.utils.ToastUtil;
import com.jxs.hotspot.widget.LazyFragmentPagerAdapter;
import com.jxs.hotspot.widget.SignInDialog;

import butterknife.OnClick;

public class MeFragment extends BaseFragment implements LazyFragmentPagerAdapter.Laziable {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance(String param1) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.image_sign_in, R.id.image_search, R.id.text_login, R.id.layout_me_assets,
            R.id.layout_modify_data, R.id.layout_me_like, R.id.layout_me_participate,
            R.id.layout_me_msg, R.id.layout_setting, R.id.layout_submission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_sign_in: {
                signIn();
            }
            break;
            case R.id.image_search: {
                ARouter.getInstance().build("/jxs/search").navigation();
            }
            break;
            case R.id.text_login: {
                ARouter.getInstance().build("/jxs/login").navigation();
            }
            break;
            case R.id.layout_me_assets:
                break;
            case R.id.layout_modify_data:
                break;
            case R.id.layout_me_like:
                break;
            case R.id.layout_me_participate:
                break;
            case R.id.layout_me_msg: {
                ARouter.getInstance().build("/jxs/message").navigation();
            }
            break;
            case R.id.layout_setting: {
                ARouter.getInstance().build("/jxs/setting").navigation();
            }
            break;
            case R.id.layout_submission:
                break;
            default:
                break;
        }
    }

    private SignInDialog mSignInDialog;

    private void signIn() {
        if (null != MeFragment.this.getActivity()) {
            mSignInDialog = new SignInDialog(MeFragment.this.getActivity(), R.style.DialogTransparent);
            mSignInDialog.setSignInOnClickListener(new SignInDialog.SignInOnClickListener() {
                @Override
                public void onSignInClick() {
                    ToastUtil.showTip(MeFragment.this.getActivity(), "哈哈哈哈哈");
                }
            });

            mSignInDialog.setCloseOnClickListener(new SignInDialog.CloseOnClickListener() {
                @Override
                public void onCloseClick() {
                    mSignInDialog.dismiss();
                }
            });
            mSignInDialog.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (null != mSignInDialog) {
            mSignInDialog.dismiss();
            mSignInDialog = null;
        }
    }
}
