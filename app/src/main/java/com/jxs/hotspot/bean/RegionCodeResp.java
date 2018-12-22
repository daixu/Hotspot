package com.jxs.hotspot.bean;

import java.io.Serializable;
import java.util.List;

public class RegionCodeResp extends BaseResp implements Serializable {

    public List<DataBean> data;

    public static class DataBean implements Serializable {
        /**
         * id : 440000198605226300
         * title : 就无况听音精
         * code : 234
         */

        public String id;
        public String countryName;
        public String countryCode;
    }
}
