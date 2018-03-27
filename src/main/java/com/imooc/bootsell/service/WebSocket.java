package com.imooc.bootsell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    private CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();


    @OnOpen
    public void OnOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("[webSockets信息],有新的连接,总数:{}", webSocketSet.size());
    }

    @OnClose
    public void OnClose(Session session) {
        webSocketSet.remove(session);
        log.info("[webSockets信息],断开连接,总数:{}", webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message) {
        log.info("[webSockets信息],收到客户端发来的的信息:{}", message);
    }

    public void sendWebSockets(String message) {
        for (WebSocket webSocket : webSocketSet) {
            log.info("[webSockets信息],广播消息,message:{}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
