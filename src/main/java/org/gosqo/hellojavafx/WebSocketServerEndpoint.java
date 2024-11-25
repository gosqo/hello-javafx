package org.gosqo.hellojavafx;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import javafx.application.Platform;

import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/echo", configurator = WebSocketConfigurator.class)  // WebSocket 경로 설정
public class WebSocketServerEndpoint {

    private static final Set<Session> sessions = new HashSet<>();

    private static HelloController controller;

    public static void setController(HelloController helloController) {
        controller = helloController;
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Connected to client: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Closed connection: " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("Received message: " + message);
        String toPrint = String
                .format("%s: %s"
                        , session.getUserProperties().get("remoteAddress")
                        , message
                );

        if (controller != null) {
            Platform.runLater(() -> controller.printMessage(toPrint));
        }

        return toPrint;  // 받은 메시지 에코
    }

    public static void broadcastMessage(String message) {
        synchronized (sessions) {
            sessions.forEach((session) -> {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText("Server: " + message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
