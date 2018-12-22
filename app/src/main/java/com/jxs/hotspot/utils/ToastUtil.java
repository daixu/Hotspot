package com.jxs.hotspot.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastUtil {

    public static void showTip(Context context, String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Toasty.normal(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showTip(Context context, int resId) {
//        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
         Toasty.normal(context, context.getString(resId), Toast.LENGTH_SHORT).show();
    }
}