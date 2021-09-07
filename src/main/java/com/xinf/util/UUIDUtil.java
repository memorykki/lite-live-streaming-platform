package com.xinf.util;

import java.util.UUID;


/**
 * @author xinf
 * @since 2021/9/7 16:38
 */
public class UUIDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
