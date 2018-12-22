package com.jxs.hotspot.view.details;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.bean.FindDetailResp;
import com.jxs.hotspot.bean.UserDetailCountResp;
import com.jxs.hotspot.relation.ShowPicRelation;
import com.jxs.hotspot.widget.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;

public class DetailHeaderView extends FrameLayout {

    private static final String NICK = "jxs";

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.ll_info)
    public LinearLayout mLlInfo;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.text_author)
    TextView mTextAuthor;
    @BindView(R.id.text_time)
    TextView mTextTime;
    @BindView(R.id.wv_content)
    WebView mWvContent;
    @BindView(R.id.video_player)
    JzvdStd mVideoPlayer;
    @BindView(R.id.text_agreement)
    TextView mTextAgreement;
    @BindView(R.id.layout_activity_progress)
    LinearLayout mLayoutActivityProgress;
    @BindView(R.id.layout_activity_ends)
    LinearLayout mLayoutActivityEnds;

    private Context mContext;

    private UserDetailCountResp mResp;

    public DetailHeaderView(Context context) {
        this(context, null);
    }

    public DetailHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.header_detail, this);
        ButterKnife.bind(this, this);
    }

    public void setDetailCount(UserDetailCountResp resp) {
        mResp = resp;
    }

    public void setDetail(FindDetailResp.DataBean dataBean, LoadWebListener listener) {
        mWebListener = listener;

        mTvTitle.setText(dataBean.title);

        mTextAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTextAgreement.setText("如何使用查看智能合约？");

        if (dataBean.status == 1) {
            mLayoutActivityProgress.setVisibility(View.VISIBLE);
            mLayoutActivityEnds.setVisibility(View.GONE);
        } else {
            mLayoutActivityProgress.setVisibility(View.GONE);
            mLayoutActivityEnds.setVisibility(View.VISIBLE);
        }

//        if (detail.media_user == null) {
//            //如果没有用户信息
//            mLlInfo.setVisibility(GONE);
//        } else {
//            if (!TextUtils.isEmpty(detail.media_user.avatar_url)) {
//                GlideApp.with(mContext)
//                        .load(detail.media_user.avatar_url)
//                        .centerCrop()
//                        .placeholder(R.mipmap.ic_launcher)
//                        .into(mIvAvatar);
//            }
//            mTextAuthor.setText(detail.media_user.screen_name);
//            mTextTime.setText(DateUtils.getShortTime(detail.publish_time * 1000L));
//        }

        mVideoPlayer.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子快长大", JzvdStd.SCREEN_WINDOW_NORMAL);

        GlideApp.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(mVideoPlayer.thumbImageView);

//        if (TextUtils.isEmpty(detail.content)) {
//            mWvContent.setVisibility(GONE);
//        }
        // 设置JS可用
        mWvContent.getSettings().setJavaScriptEnabled(true);
        // 绑定JS和Java的联系类，以及使用到的昵称
        mWvContent.addJavascriptInterface(new ShowPicRelation(mContext), NICK);

        String htmlPart1 = "<!DOCTYPE HTML html>\n" +
                "<head><meta charset=\"utf-8\"/>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no\"/>\n" +
                "</head>\n" +
                "<body>\n" +
                "<style> \n" +
                "img{width:100%!important;height:auto!important}\n" +
                " </style>";
        String htmlPart2 = "</body></html>";

        String html = htmlPart1 + dataBean.content + htmlPart2;

        mWvContent.loadUrl("https://www.v2ex.com/");
//        mWvContent.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        mWvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 添加多JS代码，为图片绑定点击函数
                addJs(view);
                if (mWebListener != null) {
                    mWebListener.onLoadFinished();
                }
            }
        });
    }

    /**
     * 添加JS代码，获取所有图片的链接以及为图片设置点击事件
     */
    private void addJs(WebView wv) {
        wv.loadUrl("javascript:(function  pic(){" +
                "var imgList = \"\";" +
                "var imgs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++){" +
                "var img = imgs[i];" +
                "imgList = imgList + img.src +\";\";" +
                "img.onclick = function(){" +
                "window.jxs.openImg(this.src);" +
                "}" +
                "}" +
                "window.jxs.getImgArray(imgList);" +
                "})()");
    }

    private LoadWebListener mWebListener;

    @OnClick({R.id.image_eye, R.id.image_next, R.id.image_answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_eye:
                break;
            case R.id.image_next: {
                ARouter.getInstance().build("/jxs/personal/detail").withObject("resp", mResp).navigation();
            }
            break;
            case R.id.image_answer: {
                // ToastUtil.showTip(mContext, "哈哈哈哈哈哈");
                // startAnswer();
                ARouter.getInstance().build("/jxs/question").navigation();
            }
            break;
            default:
                break;
        }
    }

    /**
     * 页面加载的回调
     */
    public interface LoadWebListener {
        void onLoadFinished();
    }
}
