package com.jxs.hotspot.bean;

import java.util.List;

public class SortListResp extends BaseResp {
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 330000197207257566
         * imageUrl : http://dummyimage.com/120x240
         * name : 林洋
         * title : 下度土清国
         * count : 234
         * type : 1
         * imageAvatar : http://dummyimage.com/160x600
         * money : 43242
         */

        public String id;
        public String imageUrl;
        public String name;
        public String title;
        public int count;
        public int type;
        public String imageAvatar;
        public String money;
    }
}
