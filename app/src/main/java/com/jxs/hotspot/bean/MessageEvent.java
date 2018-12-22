package com.jxs.hotspot.bean;

public class MessageEvent {
    public int type;
    public String message;

    public MessageEvent(int type, String message) {
        this.type = type;
        this.message = message;
    }
}