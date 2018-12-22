package com.jxs.hotspot.bean;

public class RewardInfoResp extends BaseResp {
    /**
     * data : {"id":"310000200201108616","title":"而反条重","unit":"ETH","minAmount":0.01}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * id : 310000200201108616
         * title : 而反条重
         * unit : ETH
         * minAmount : 0.01
         */

        public String id;
        public String title;
        public String unit;
        public double minAmount;
    }
}
