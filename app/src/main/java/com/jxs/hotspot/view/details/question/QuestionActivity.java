package com.jxs.hotspot.view.details.question;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.QuestionAdapter;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.QuestionListResp;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

@Route(path = "/jxs/question")
public class QuestionActivity extends BaseActivity implements QuestionContract.View {
    @BindView(R.id.recycler_answer)
    RecyclerView mRecyclerAnswer;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    private QuestionAdapter mQuestionAdapter;

    private List<QuestionListResp.DataBean> mList = new ArrayList<>();
    private List<QuestionListResp.DataBean.AnswerBean> mAnswerList = new ArrayList<>();

    @Inject
    QuestionContract.Presenter mPresenter;

    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        mRecyclerAnswer.setLayoutManager(new LinearLayoutManager(this));
        mQuestionAdapter = new QuestionAdapter(R.layout.item_question_list, mAnswerList);
        mRecyclerAnswer.setAdapter(mQuestionAdapter);
        mQuestionAdapter.setEnableLoadMore(false);
    }

    private void initData() {
        mPresenter.takeView(this);
        mPresenter.getQuestionList();
    }

    private void initListener() {
        mQuestionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final QuestionListResp.DataBean.AnswerBean bean = mAnswerList.get(position);
                switch (view.getId()) {
                    case R.id.image_answer: {
                        for (QuestionListResp.DataBean.AnswerBean answerBean: mAnswerList) {
                            answerBean.isClick = false;
                        }
                        bean.isClick = !bean.isClick;
                        mQuestionAdapter.notifyDataSetChanged();


                    }
                    break;
                    default:
                        break;
                }
            }
        });
//        mQuestionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtil.showTip(QuestionActivity.this, "position= " + position);
//
//                if (null != mList && mList.size() >= mPosition) {
//                    mPosition++;
//                    mTextTitle.setText(mList.get(mPosition).title);
//                    mAnswerList.clear();
//                    mAnswerList.addAll(mList.get(mPosition).answer);
//                    mQuestionAdapter.setNewData(mAnswerList);
//                    mQuestionAdapter.loadMoreComplete();
//                }
//            }
//        });
    }

    @OnClick(R.id.btn_exit)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getQuestionListSuccess(QuestionListResp resp) {
        if (null != resp.data) {
            List<QuestionListResp.DataBean> list = resp.data;
            mList.clear();
            mList.addAll(list);

            mPosition = 0;
            mTextTitle.setText(mList.get(mPosition).title);

            mAnswerList.clear();
            mAnswerList.addAll(mList.get(mPosition).answer);
            mQuestionAdapter.setNewData(mAnswerList);
            mQuestionAdapter.loadMoreComplete();
        }
    }

    @Override
    public void getQuestionListFailure(String msg) {
        Timber.e("getQuestionListFailure msg=" + msg);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
