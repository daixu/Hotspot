package com.jxs.hotspot.bean;

import java.io.Serializable;

public class UserDetailCountResp extends BaseResp implements Serializable {
    /**
     * msg : ok
     * data : {"original":5,"recommend":11,"like":8,"reward":3}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * original : 5
         * recommend : 11
         * like : 8
         * reward : 3
         */

        public int original;
        public int recommend;
        public int like;
        public int reward;
    }
}
