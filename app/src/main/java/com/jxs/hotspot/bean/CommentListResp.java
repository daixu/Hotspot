package com.jxs.hotspot.bean;

import java.util.List;

public class CommentListResp extends BaseResp {
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 420000197605185264
         * name : 谢杰
         * title : 则太联白
         * count : 23
         * imageAvatar : http://ww3.sinaimg.cn/mw600/0073tLPGgy1fropg9qja1j31kw2dce81.jpg
         * time : 4561324654123
         * replyList : [{"id":12,"replyName":{"type":1,"name":"龚洋"},"commentator":{"type":2,"name":"梁娟"},"content":"白子增断取飞当"},{"id":12,"replyName":{"type":1,"name":"武静"},"commentator":{"type":2,"name":"方平"},"content":"办状共从"},{"id":12,"replyName":{"type":1,"name":"石平"},"commentator":{"type":2,"name":"雷芳"},"content":"形效工江"},{"id":12,"replyName":{"type":1,"name":"罗勇"},"commentator":{"type":2,"name":"萧超"},"content":"细干受量速装"},{"id":12,"replyName":{"type":1,"name":"韩芳"},"commentator":{"type":2,"name":"秦霞"},"content":"调只位"},{"id":12,"replyName":{"type":1,"name":"宋强"},"commentator":{"type":2,"name":"廖秀英"},"content":"程百解是"},{"id":12,"replyName":{"type":1,"name":"孔平"},"commentator":{"type":2,"name":"秦洋"},"content":"明号思"},{"id":12,"replyName":{"type":1,"name":"易芳"},"commentator":{"type":2,"name":"郭娜"},"content":"过深光转江"},{"id":12,"replyName":{"type":1,"name":"廖霞"},"commentator":{"type":2,"name":"任艳"},"content":"多习水过展"},{"id":12,"replyName":{"type":1,"name":"沈娟"},"commentator":{"type":2,"name":"文明"},"content":"法并革今应积"},{"id":12,"replyName":{"type":1,"name":"雷艳"},"commentator":{"type":2,"name":"马明"},"content":"样真表达之千斗"},{"id":12,"replyName":{"type":1,"name":"何丽"},"commentator":{"type":2,"name":"林静"},"content":"从果样眼音为"},{"id":12,"replyName":{"type":1,"name":"杜芳"},"commentator":{"type":2,"name":"常超"},"content":"斯导问"},{"id":12,"replyName":{"type":1,"name":"康秀英"},"commentator":{"type":2,"name":"魏磊"},"content":"那所能合"},{"id":12,"replyName":{"type":1,"name":"沈娟"},"commentator":{"type":2,"name":"马敏"},"content":"她高选"}]
         */

        public String id;
        public String name;
        public String title;
        public int count;
        public String imageAvatar;
        public long time;
        public List<ReplyListBean> replyList;

        public static class ReplyListBean {
            /**
             * id : 12
             * replyName : {"type":1,"name":"龚洋"}
             * commentator : {"type":2,"name":"梁娟"}
             * content : 白子增断取飞当
             */

            public int id;
            public ReplyNameBean replyName;
            public CommentatorBean commentator;
            public String content;

            public static class ReplyNameBean {
                /**
                 * type : 1
                 * name : 龚洋
                 */

                public int type;
                public String name;
            }

            public static class CommentatorBean {
                /**
                 * type : 2
                 * name : 梁娟
                 */

                public int type;
                public String name;
            }
        }
    }
}
