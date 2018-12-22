package com.jxs.hotspot.bean;

import java.util.List;

public class SearchHotWordResp extends BaseResp {
    /**
     * message : ok
     * data : [{"id":"450000200107150958","name":"邵军"},{"id":"500000200805176148","name":"宋军"},{"id":"370000197612305979","name":"曾娟"},{"id":"610000198509296677","name":"林桂英"},{"id":"370000201211192140","name":"尹静"}]
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 450000200107150958
         * name : 邵军
         */

        public String id;
        public String name;
    }
}
