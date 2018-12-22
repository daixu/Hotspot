package com.jxs.hotspot.bean;

import static com.jxs.hotspot.common.Constant.PAGE_SIZE;

public class BaseReq {
    public String id;
    public String userId;
    public int pageNum;
    public int terminal = 3;
    public int pageSize = PAGE_SIZE;
}