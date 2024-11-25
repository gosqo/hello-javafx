package org.gosqo.hellojavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.server.Server;

public class HelloController {

    private boolean isServerMode = false;

    @FXML
    private Label welcomeText;  // 레이블
    @FXML
    private TextField portInputField;  // 포트 입력창
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField messageInputField;
    @FXML
    private TextField serverIpField, serverPortField; // 클라이언트 모드 입력 필드
    @FXML
    private RadioButton serverModeRadioButton;
    @FXML
    private RadioButton clientModeRadioButton;
    @FXML
    private Button startServerButton, joinServerButton;
    @FXML
    private ToggleGroup modeToggleGroup;

    @FXML
    public void initialize() {
        portInputField.setText("8070");
        serverIpField.setText("127.0.0.1");
        serverPortField.setText("8070");

        // ToggleGroup 생성 및 라디오 버튼에 설정
        modeToggleGroup = new ToggleGroup();
        serverModeRadioButton.setToggleGroup(modeToggleGroup);
        clientModeRadioButton.setToggleGroup(modeToggleGroup);

        // 초기값 설정
        clientModeRadioButton.setSelected(true);
        isServerMode = false;

        toggleModeUI();

        // 선택 변경 리스너 추가
        modeToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == serverModeRadioButton) {
                isServerMode = true;
            } else if (newToggle == clientModeRadioButton) {
                isServerMode = false;
            }
            toggleModeUI();
        });
    }

    private void toggleModeUI() {
        serverIpField.setVisible(!isServerMode);
        serverPortField.setVisible(!isServerMode);
        joinServerButton.setVisible(!isServerMode);

        portInputField.setVisible(isServerMode);
        startServerButton.setVisible(isServerMode);
    }

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

    public void onJoinServerButtonClick() {
        String ip = serverIpField.getText();
        String portText = serverPortField.getText();
        try {
            int port = Integer.parseInt(portText.trim());
            startWebSocketClient(ip, port);
        } catch (NumberFormatException e) {
            messageArea.appendText("[Error]: Invalid server IP or port.\n");
        }
    }

    private void startWebSocketClient(String ip, int port) {
        try {
            ClientManager client = ClientManager.createClient();
            String uri = "ws://" + ip + ":" + port + "/echo";

            WebSocketClient webSocketClient = new WebSocketClient(uri, messageArea);
            messageArea.appendText("[Info]: Connected to server at " + uri + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            messageArea.appendText("[Info]: Cannot establish socket.\n");
        }
    }

    public void onSendMessageButtonClick() {
        String message = messageInputField.getText();

        if (message != null && !message.isEmpty()) {

            if (isServerMode) {

                WebSocketServerEndpoint.broadcastMessage(message);
                messageArea.appendText("me: " + message + "\n"); // 로컬 표시
                return;
            }

            WebSocketClient.sendMessage(message);
        }
    }

    public void printMessage(String message) {
        messageArea.appendText(message + "\n");
    }

    // WebSocket 서버를 실행하는 메소드
    public void startWebSocketServer(int port) {
        // WebSocket 서버 설정
        try {
            // WebSocket 서버 객체 생성
            Server server = new Server("localhost", port, "/", null, WebSocketServerEndpoint.class);
            server.start();

            WebSocketServerEndpoint.setController(this);

            // 성공 메시지
            messageArea.appendText("WebSocket Server started on port " + port + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            messageArea.appendText("WebSocket Server started FAILED on port " + port + "\n");
        }
    }
}
