package com.jxs.hotspot.bean;

public class BaseResp {

    public int code;
    public int status;
    public String msgEn;
    public String msgZh;

    public boolean isOk() {
        return status == 200;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseResp{");
        sb.append("code=").append(code);
        sb.append(",status=").append(status).append("\'");
        sb.append(",msgEn=").append(msgEn).append("\'");
        sb.append(",msgZh=").append(msgZh).append("\'");
        sb.append("}");
        return sb.toString();
    }
}