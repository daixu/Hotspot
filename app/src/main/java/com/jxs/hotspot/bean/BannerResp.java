package com.jxs.hotspot.bean;

import java.util.List;

public class BannerResp extends BaseResp {

    public List<ResultBean> result;

    public static class ResultBean {
        /**
         * bannerId : 1
         * imageAddr : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543400141272&di=fd26c609c3f4f79d8543768e892a0113&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20170906%2F58cdb24be3624488ad3e8d3d00b4585f.jpeg
         * linkAddr : https://www.baidu.com
         * appendTime : null
         * sortId : 1
         * isDelete : null
         */

        public String bannerId;
        public String imageAddr;
        public String linkAddr;
        public Object appendTime;
        public long sortId;
        public Object isDelete;
    }
}
