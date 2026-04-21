package CodeRoom.WebSocket;

import CodeRoom.MessageHandler.MessageHandler;
import CodeRoom.RoomHandler.SessionInfo;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

public class EditorWebSocketHandler extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions = new HashSet<>();

    private MessageHandler messageHandler = new MessageHandler();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Client connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.session = session;
        messageHandler.handleMessage(payload, sessionInfo);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }
}