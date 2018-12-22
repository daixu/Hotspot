package com.jxs.hotspot.bean;

public class LoginResp extends BaseResp {

    /**
     * result : {"token":"43e25eea1d78414bbaaca91f620e68de"}
     */

    public ResultBean result;

    public static class ResultBean {
        /**
         * token : 43e25eea1d78414bbaaca91f620e68de
         */

        public String token;
    }
}
