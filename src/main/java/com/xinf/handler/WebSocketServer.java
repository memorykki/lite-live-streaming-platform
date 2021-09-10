package com.xinf.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;

/**
 * 转发消息格式
 * "type", 类型, "message", 消息内容, "time", 当前时间, "form", 来自用户
 * @author xinf
 * @since 2021/9/10 14:50
 */
@Slf4j
@ServerEndpoint(value = "/websocketConn/{userId}")
@Component
public class WebSocketServer {
    /** 记录当前在线连接数 */
    static private LongAdder onlineCount = new LongAdder();
    static private Map<Long, Session> onLineSessions = new ConcurrentHashMap<Long, Session>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") long userId) {
        onlineCount.increment();
        onLineSessions.put(userId, session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        onlineCount.decrement();
        onLineSessions.remove(userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        // 转发消息
        onLineSessions.entrySet().parallelStream().forEach(e -> sendMessage(message, e.getValue()));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("客户端[{}]发生错误, err:{}", session.getId(), error.getMessage());
    }

    /**
     *  发送消息给用户，用于通知
     * @param userId
     * @param message
     */
    public void sendMessToUser(long userId, String message) {
        onLineSessions.computeIfPresent(userId, (k, v) -> {
            sendMessage(message, v);
            return v;
        });
    }

    /**
     *  发送消息给用户，用于通知
     * @param userId
     * @param supplier
     */
    public void sendMessToUser(long userId, Supplier<String> supplier) {
        onLineSessions.computeIfPresent(userId, (k, v) -> {
            sendMessage(supplier.get(), v);
            return v;
        });
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session session) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端[{}]失败：{}", session.getId(), e.getMessage());
        }
    }

    public Long getOnlineCount() {
        return onlineCount.longValue();
    }
}
