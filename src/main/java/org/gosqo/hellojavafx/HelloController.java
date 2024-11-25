package org.gosqo.hellojavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.glassfish.tyrus.server.Server;

public class HelloController {

    @FXML
    private Label welcomeText;  // 레이블
    @FXML
    private TextField portInputField;  // 포트 입력창

    @FXML
    private TextArea messageArea;

    public void printMessage(String message) {
        messageArea.appendText(message + "\n");
    }

    // WebSocket 서버를 실행하는 메소드
    public void onStartServerButtonClick() {
        // 입력된 포트 번호 가져오기
        String portText = portInputField.getText();

        try {
            int port = Integer.parseInt(portText.trim());
            startWebSocketServer(port);
        } catch (NumberFormatException e) {
            welcomeText.setText("Invalid port number!");
        }
    }

    public void startWebSocketServer(int port) {
        // WebSocket 서버 설정
        try {
            // WebSocket 서버 객체 생성
            Server server = new Server("localhost", port, "/", null, WebSocketServerEndpoint.class);
            server.start();

            WebSocketServerEndpoint.setController(this);

            // 성공 메시지
            welcomeText.setText("WebSocket Server started on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
            welcomeText.setText("Failed to start WebSocket server.");
        }
    }
}
