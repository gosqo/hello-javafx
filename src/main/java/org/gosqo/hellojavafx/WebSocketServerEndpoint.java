package org.gosqo.hellojavafx;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import javafx.application.Platform;

@ServerEndpoint(value = "/echo", configurator = WebSocketConfigurator.class)  // WebSocket 경로 설정
public class WebSocketServerEndpoint {

    private static HelloController controller;

    public static void setController(HelloController helloController) {
        controller = helloController;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to client: " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("Received message: " + message);

        if (controller != null) {
            Platform.runLater(() ->
                    controller.printMessage(String
                            .format("%s: %s"
                                    , session.getUserProperties().get("remoteAddress")
                                    , message
                            )
                    )
            );
        }

        return "Echo: " + message;  // 받은 메시지 에코
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Closed connection: " + session.getId());
    }
}
