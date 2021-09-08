package com.xinf.util;

import java.util.Collection;
import java.util.Iterator;

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

    // 打印集合字符串
    public static<T> String getCollectionString(Collection<T> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().toString() + ", ");
        }
        sb.append("}");
        return sb.toString();
    }
}
