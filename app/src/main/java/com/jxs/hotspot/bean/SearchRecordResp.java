package com.jxs.hotspot.bean;

import java.util.List;

public class SearchRecordResp extends BaseResp {
    /**
     * message : ok
     * data : [{"id":"460000198702242664","title":"韩秀兰"},{"id":"450000200711232749","title":"谢平"},{"id":"350000199510064460","title":"毛秀英"},{"id":"120000200402206575","title":"曾静"},{"id":"110000197911261410","title":"余刚"},{"id":"64000020140111515X","title":"林洋"},{"id":"360000200109217835","title":"乔杰"}]
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 460000198702242664
         * title : 韩秀兰
         */

        public String id;
        public String title;
    }
}
