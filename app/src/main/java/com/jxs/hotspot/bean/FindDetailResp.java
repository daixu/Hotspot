package com.jxs.hotspot.bean;

public class FindDetailResp extends BaseResp {

    /**
     * msg : ok
     * data : {"id":"810000200710204444","title":"提反划机角到","imageUrl":"http://dummyimage.com/728x90","count":234,"type":1,"imageAvatar":"http://dummyimage.com/468x60","name":"宋伟","time":32131231312,"content":"日没目","status":1,"money":"4235.01E TH","endTime":42432424232}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * id : 810000200710204444
         * title : 提反划机角到
         * imageUrl : http://dummyimage.com/728x90
         * count : 234
         * type : 1
         * imageAvatar : http://dummyimage.com/468x60
         * name : 宋伟
         * time : 32131231312
         * content : 日没目
         * status : 1
         * money : 4235.01E TH
         * endTime : 42432424232
         */

        public String id;
        public String title;
        public String imageUrl;
        public int count;
        public int type;
        public String imageAvatar;
        public String name;
        public long time;
        public String content;
        public int status;
        public String money;
        public long endTime;
    }
}
