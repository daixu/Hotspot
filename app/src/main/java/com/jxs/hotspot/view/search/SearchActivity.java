package com.jxs.hotspot.view.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.nukc.stateview.StateView;
import com.jxs.hotspot.R;
import com.jxs.hotspot.adapter.SearchAdapter;
import com.jxs.hotspot.base.BaseActivity;
import com.jxs.hotspot.bean.SearchHotWordResp;
import com.jxs.hotspot.bean.SearchResp;
import com.jxs.hotspot.db.SearchModel;
import com.jxs.hotspot.db.SearchModel_Table;
import com.jxs.hotspot.utils.ToastUtil;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

@Route(path = "/jxs/search")
public class SearchActivity extends BaseActivity implements SearchContract.View {

    @BindView(R.id.text_hot_word)
    TextView mTextHotWord;
    @BindView(R.id.text_search_record)
    TextView mTextSearchRecord;
    @BindView(R.id.layout_content)
    LinearLayout mLayoutContent;
    @BindView(R.id.edit_search)
    EditText mEditSearch;
    @BindView(R.id.recycler_search)
    RecyclerView mRecyclerSearch;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    private List<String> mSearchRecordList = new ArrayList<>();
    private List<SearchResp.DataBean> mSearchList = new ArrayList<>();

    @BindView(R.id.flow_search_hot_word)
    TagFlowLayout mFlowSearchHotWord;
    @BindView(R.id.flow_search_record)
    TagFlowLayout mFlowSearchRecord;

    private SearchAdapter mSearchAdapter;

    @Inject
    SearchContract.Presenter mPresenter;

    private StateView mStateView;

    private String mSearchString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        mStateView = StateView.inject(mLayoutContent);

        mRecyclerSearch.setLayoutManager(new LinearLayoutManager(this));
        mSearchAdapter = new SearchAdapter(R.layout.item_search, mSearchList);
        mRecyclerSearch.setAdapter(mSearchAdapter);
        mSearchAdapter.setEnableLoadMore(false);
    }

    private void initData() {
        mStateView.showLoading();
        mPresenter.takeView(this);

        mPresenter.getSearchHotWord();

        List<SearchModel> list = SQLite.select().from(SearchModel.class).limit(10).orderBy(SearchModel_Table.id, false).queryList();
        for (SearchModel model : list) {
            mSearchRecordList.add(model.name);
        }

        mFlowSearchRecord.setAdapter(new TagAdapter<String>(mSearchRecordList) {
            @Override
            public View getView(FlowLayout parent, int position, String string) {
                TextView textName = (TextView) getLayoutInflater().inflate(R.layout.item_search_record,
                        parent, false);
                textName.setText(string);
                return textName;
            }
        });

        mFlowSearchRecord.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ToastUtil.showTip(SearchActivity.this, mSearchRecordList.get(position));
                return true;
            }
        });
    }

    private void initListener() {
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    String keyword = mEditSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        ToastUtil.showTip(SearchActivity.this, "请输入搜索关键字");
                        return false;
                    }
                    // 搜索功能主体
                    search(keyword);
                    return true;
                }
                return false;
            }
        });

        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keywords = mEditSearch.getText().toString();
                if (keywords.length() == 0) {
                    mRecyclerSearch.setVisibility(View.GONE);
                    mScrollView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void search(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            SearchModel searchModule = new Select().from(SearchModel.class).where(SearchModel_Table.name.is(keyword)).querySingle();
            if (null == searchModule) {
                SearchModel searchBean = new SearchModel();
                searchBean.name = keyword;
                searchBean.save();
            }
            hideKeyboard();

            mSearchString = keyword;
            mPresenter.search(keyword);
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (null != imm && imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @OnClick({R.id.text_cancel, R.id.text_clear_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_cancel: {
                finish();
            }
            break;
            case R.id.text_clear_record: {
                Delete.table(SearchModel.class);

                mSearchRecordList.clear();
                mFlowSearchRecord.getAdapter().notifyDataChanged();
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void getSearchHotWordSuccess(SearchHotWordResp resp) {
        if (null != resp.data) {
            mStateView.showContent();

            mFlowSearchHotWord.setAdapter(new TagAdapter<SearchHotWordResp.DataBean>(resp.data) {
                @Override
                public View getView(FlowLayout parent, int position, SearchHotWordResp.DataBean dataBean) {
                    TextView textName = (TextView) getLayoutInflater().inflate(R.layout.item_search_hot_word,
                            parent, false);
                    textName.setText(dataBean.name);
                    return textName;
                }
            });
        }
    }

    @Override
    public void getSearchHotWordFailure(String msg) {
        Timber.e("getSearchHotWordFailure= " + msg);
    }

    @Override
    public void getSearchListSuccess(SearchResp resp) {
        mScrollView.setVisibility(View.GONE);
        mRecyclerSearch.setVisibility(View.VISIBLE);

        List<SearchResp.DataBean> list = resp.data;
        mSearchList.clear();
        mSearchList.addAll(list);
        mSearchAdapter.setNewData(mSearchList);
        mSearchAdapter.loadMoreComplete();
    }

    @Override
    public void getSearchListFailure(String msg) {
        Timber.e("getSearchListFailure= " + msg);
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
