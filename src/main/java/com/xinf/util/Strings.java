package com.xinf.util;

/**
 * @author xinf
 * @since 2021/9/1 20:46
 */
public final class Strings {
    // 字符串是否全由数字组成
    public static boolean isConstitutedByDigit(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        char[] res = s.toCharArray();
        for (char c : res) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // 字符串是邮箱
    public static boolean isEmail(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return s.contains("@");
    }
}
