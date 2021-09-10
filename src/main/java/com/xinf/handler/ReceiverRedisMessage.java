package com.xinf.handler;

import lombok.extern.slf4j.Slf4j;

/**
 *  接收redis消息并处理
 *
 * @author xinf
 * @since 2021/9/10 15:15
 */
@Slf4j
@Deprecated
public class ReceiverRedisMessage {

    /**
     * 队列消息接收方法
     *
     * @param jsonMsg
     */
    public void receiveMessage(String jsonMsg) {
        log.info("获取到一条弹幕");
        try {
            System.out.println(jsonMsg);
            log.info("[消费REDIS消息队列数据成功.]");
        } catch (Exception e) {
            log.error("[消费REDIS消息队列数据失败，失败信息:{}]", e.getMessage());
        }
    }
}
