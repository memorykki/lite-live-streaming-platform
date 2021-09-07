package com.xinf.constant;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author xinf
 * @since 2021/9/6 17:43
 */
public class RoomType {
    public static BiMap<String, Integer> res = HashBiMap.create(5);
    static {
        res.put("游戏", 1);
        res.put("唱歌", 2);
        res.put("跳舞", 3);
        res.put("放映", 4);
        res.put("整活", 5);
    }
}
