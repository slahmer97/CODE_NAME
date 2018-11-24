package com.codename.demo.game.websocket;

import com.codename.demo.GameScheduler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class MessageHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // The WebSocket has been closed
        GameScheduler.playerHasDisconnected(session,status);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        GameScheduler.newPlayerHasConnected(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        System.out.println("==============  "+textMessage.getPayload());
        GameScheduler.newPlayerMessage(session,textMessage);
    }
}