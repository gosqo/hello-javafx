package org.gosqo.hellojavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Label welcomeText;  // 레이블
    @FXML
    private TextField portInputField;  // 포트 입력창

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
        //do something ...

        welcomeText.setText("WebSocket Server started on port " + port);
    }
}
