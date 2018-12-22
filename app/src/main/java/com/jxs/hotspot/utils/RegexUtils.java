package com.jxs.hotspot.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile
     *            移动、联通、电信运营商的号码段
     *            <p>
     *            移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *            </p>
     *            <p>
     *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *            </p>
     *            <p>
     *            电信的号段：133、153、180（未启用）、189
     *            </p>
     *            <p>
     *            虚拟运营商 170，171
     *            </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[345678]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证身份证号码
     *
     * @param idCard
     *            居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

}