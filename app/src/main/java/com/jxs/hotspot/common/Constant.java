package com.jxs.hotspot.common;

public class Constant {

    public static int PAGE_SIZE = 20;
    public interface Url {
//        String API_SERVER_URL = "http://rap2api.taobao.org/app/mock/13994//";
//         String API_SERVER_URL = "http://rap2api.taobao.org/app/mock/104603//";
        String API_SERVER_URL = "http://192.168.1.38:8080/api/v1.0/";
    }

    public interface EventType {
        int EVENT_MAIN_TYPE = 101;
        int EVENT_RESET_LOGIN_PWD_TYPE = 201;
        int EVENT_SET_PAY_PWD_TYPE = 301;
        int EVENT_ADD_BAND_CARD_TYPE = 401;
        int EVENT_WITHDRAW_SUCCESS = 501;
        int EVENT_PAY_SUCCESS = 601;
    }

    public interface EventMessage {
        String FINISH_ACTIVITY = "finish_activity";
        String ADD_BAND_CARD_SUCCESS = "add_band_card_success";
        String WITHDRAW_SUCCESS = "withdraw_success";
        String PAY_SUCCESS = "pay_success";
        String PAY_BALANCE_SUCCESS = "pay_balance_success";
        String PAY_BALANCE_CANCEL = "pay_cancel";
    }

    public interface Length {
        int MIN_LOGIN_PWD_LENGTH = 8;
        int MIN_PAY_PWD_LENGTH = 6;
    }
}