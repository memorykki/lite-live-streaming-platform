package com.xinf.util;

import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * @author xinf
 * @since 2021/9/1 20:46
 */
public final class Strings {

    public static Gson gson = new Gson();

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

    public static String getFileType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "";
        }
        String contentType = file.getContentType();
        String ans = contentType == null ? "" : (
                "." + contentType.substring(contentType.lastIndexOf('/') + 1, contentType.length()));

        return ans;
    }

    public static String getJsonString(Object o) {
        return gson.toJson(o);
    }

    public static String getDateString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
