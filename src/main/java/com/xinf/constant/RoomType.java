package com.xinf.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xinf
 * @since 2021/9/6 17:43
 */
public class RoomType {
    public static Map<String, Integer> res = new HashMap<>();
    static {
        res.put("游戏", 1);
        res.put("唱歌", 2);
        res.put("跳舞", 3);
        res.put("放映", 4);
        res.put("整活", 5);
    }

}
