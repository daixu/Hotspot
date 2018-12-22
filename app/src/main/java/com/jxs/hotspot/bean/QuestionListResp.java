package com.jxs.hotspot.bean;

import java.util.List;

public class QuestionListResp extends BaseResp {
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 520000197011236600
         * title : 数线专口
         * answer : [{"id":"370000201806249636","option":"A","title":"农及事员王农"},{"id":"500000199203051772","option":"B","title":"各带历识"},{"id":"650000197405103554","option":"C","title":"少完般酸"},{"id":"820000197101231380","option":"D","title":"育利布周信各"}]
         */

        public String id;
        public String title;
        public List<AnswerBean> answer;

        public static class AnswerBean {
            /**
             * id : 370000201806249636
             * option : A
             * title : 农及事员王农
             */

            public String id;
            public String option;
            public String title;
            public boolean isClick;
        }
    }
}
