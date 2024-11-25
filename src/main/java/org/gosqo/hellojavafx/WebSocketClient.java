package org.gosqo.hellojavafx;

import jakarta.websocket.*;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.net.URI;

@ClientEndpoint
public class WebSocketClient {
    private static Session session;
    private final TextArea messageArea;

    public WebSocketClient(String serverUri, TextArea messageArea) {
        this.messageArea = messageArea;

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(serverUri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session serverSession) {
        session = serverSession;
        System.out.println("Connected to server: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message) {
        // 서버로부터 받은 메시지를 JavaFX UI에 표시
        Platform.runLater(() -> messageArea.appendText(message + "\n")
        );
    }

    @OnClose
    public void onClose(Session session) {
        Platform.runLater(() -> messageArea.appendText(
                "session closed: " + session.getId() + "\n")
        );
        System.out.println("Disconnected from server: " + session.getId());
    }

    public static void sendMessage(String message) {
        try {
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(message);
            } else {
                System.out.println("Session is not opened. cannot send message.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
