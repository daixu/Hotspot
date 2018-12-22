package com.jxs.hotspot.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditInputFilter implements InputFilter {

    /**
     * 最大数字
     */
    private static final int MAX_VALUE = Integer.MAX_VALUE;

    /**
     * 小数点后的数字的位数
     */
    private static final int POINTER_LENGTH = 2;

    private static final String POINTER = ".";

    private Pattern mPattern;

    private Context mContext;

    public EditInputFilter(Context context) {
        //用于匹配输入的是0-9  .  这几个数字和字符
        mPattern = Pattern.compile("([0-9]|\\.)*");
        mContext = context;
    }

    /**
     * source    新输入的字符串
     * start    新输入的字符串起始下标，一般为0
     * end    新输入的字符串终点下标，一般为source长度-1
     * dest    输入之前文本框内容
     * dstart    原内容起始坐标，一般为0
     * dend    原内容终点坐标，一般为dest长度-1
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();
        // 验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            // 保证小数点不在第一个位置
            if (dstart == 0 && destText.indexOf(POINTER) == 1) {
                return "0";
            }
            return "";
        }
        Matcher matcher = mPattern.matcher(source);
        // 已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "";
            } else {
                // 只能输入一个小数点
                if (POINTER.equals(source)) {
                    return "";
                }
            }
            // 验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(POINTER);
            int length = destText.trim().length() - index;
            if (length > POINTER_LENGTH && dstart > index) {
                return "";
            }
        } else {
            // 没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
            if (!matcher.matches()) {
                return "";
            } else {
                // 第一个位置输入小数点的情况
                if ((POINTER.equals(source)) && dstart == 0) {
                    return "0.";
                } else if ("0".equals(source) && dstart == 0) {
                    //用于修复能输入多位0
                    return "";
                }
            }
        }
        // 修复当光标定位到第一位的时候 还能输入其他的    这个是为了修复以下的情况
        String first = destText.substring(0, dstart);

        String second = destText.substring(dstart, destText.length());
        String sum = first + sourceText + second;
        // 验证输入金额的大小
        double sumText = Double.parseDouble(sum);
        // 这里得到输入完之后需要计算的金额  如果这个金额大于了事先设定的金额,那么久直接返回  不需要加入输入的字符
        if (sumText > MAX_VALUE) {
            return dest.subSequence(dstart, dend);
        }
        //如果输入的金额小于事先规定的金额
        return dest.subSequence(dstart, dend) + sourceText;
    }
}