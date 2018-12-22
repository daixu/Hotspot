package com.jxs.hotspot.view.details.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseFragment;

public class AnswerFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


    public AnswerFragment() {
        // Required empty public constructor
    }

    public static AnswerFragment newInstance(String param1) {
        AnswerFragment fragment = new AnswerFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_answer;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

}
