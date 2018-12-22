package com.jxs.hotspot.net.api.service;

import com.jxs.hotspot.bean.BannerResp;
import com.jxs.hotspot.bean.BaseReq;
import com.jxs.hotspot.bean.BaseResp;
import com.jxs.hotspot.bean.CommentListResp;
import com.jxs.hotspot.bean.FindDetailResp;
import com.jxs.hotspot.bean.FindListResp;
import com.jxs.hotspot.bean.LoginReq;
import com.jxs.hotspot.bean.LoginResp;
import com.jxs.hotspot.bean.OriginalListResp;
import com.jxs.hotspot.bean.QuestionListResp;
import com.jxs.hotspot.bean.RegionCodeResp;
import com.jxs.hotspot.bean.RegisterReq;
import com.jxs.hotspot.bean.RewardInfoResp;
import com.jxs.hotspot.bean.RewardReq;
import com.jxs.hotspot.bean.SearchHotWordResp;
import com.jxs.hotspot.bean.SearchReq;
import com.jxs.hotspot.bean.SearchResp;
import com.jxs.hotspot.bean.SortListResp;
import com.jxs.hotspot.bean.SortResp;
import com.jxs.hotspot.bean.UserDetailCountResp;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {

    @GET("banner/show")
    Flowable<BannerResp> getBanner();

    @POST("find/list")
    Flowable<FindListResp> getFindList(@Body BaseReq req);

    @POST("find/detail")
    Flowable<FindDetailResp> getDetail(@Body BaseReq req);

    @POST("comment/list")
    Flowable<CommentListResp> getCommentList(@Body BaseReq req);

    @POST("reward")
    Flowable<BaseResp> reward(@Body RewardReq req);

    @POST("reward/info")
    Flowable<RewardInfoResp> getRewardInfo(@Body BaseReq req);

    @POST("question/list")
    Flowable<QuestionListResp> getQuestionList(@Body BaseReq req);

    @GET("search/hot/word")
    Flowable<SearchHotWordResp> getSearchHotWord();

    @POST("search/keywords")
    Flowable<SearchResp> search(@Body SearchReq req);

    @POST("user/detail/count")
    Flowable<UserDetailCountResp> getUserDetailCount();

    @POST("user/original/list")
    Flowable<OriginalListResp> getOriginalList();

    @POST("sort")
    Flowable<SortResp> getSort(@Body BaseReq req);

    @POST("sort/list")
    Flowable<SortListResp> getSortList(@Body BaseReq req);

    @GET("code/phone")
    Flowable<BaseResp> getPhoneCode(@Query("areaCode") String areaCode, @Query("phone") String phone);

    @GET("code/email")
    Flowable<BaseResp> getEmailCode(@Query("email") String email);

    @POST("account/register")
    Flowable<BaseResp> registerPhone(@Body RegisterReq req);

    @POST("account/login")
    Flowable<LoginResp> loginPhone(@Body LoginReq req);

    @GET("region/code")
    Flowable<RegionCodeResp> regionCode();

    @GET("my/comment")
    Flowable<BaseResp> getMyComment();
}
