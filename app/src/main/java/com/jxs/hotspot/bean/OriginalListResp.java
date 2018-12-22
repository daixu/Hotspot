package com.jxs.hotspot.bean;

import java.util.List;

public class OriginalListResp extends BaseResp {
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 320000197010157535
         * imageUrl : http://ww3.sinaimg.cn/mw600/0073tLPGgy1fropg9qja1j31kw2dce81.jpg
         * name : 田军
         * title : 构族外六
         * count : 234
         * type : 1
         * imageAvatar : http://ww3.sinaimg.cn/mw600/0073tLPGgy1fropg9qja1j31kw2dce81.jpg
         * imageIcon : http://ww3.sinaimg.cn/mw600/0073tLPGgy1fropg9qja1j31kw2dce81.jpg
         * time : 4561324654123
         */

        public String id;
        public String imageUrl;
        public String name;
        public String title;
        public int count;
        public int type;
        public String imageAvatar;
        public String imageIcon;
        public long time;
    }
}
