package com.jxs.hotspot.bean;

import java.util.List;

public class SortResp extends BaseResp {
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 530000200904094135
         * name : 二南海济率
         * imageUrl : http://dummyimage.com/300x600
         */

        public String id;
        public String name;
        public String imageUrl;
    }
}
