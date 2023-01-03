package com.trocamensagagens.view;

import com.trocamensagagens.controller.ChatController;
import com.trocamensagagens.model.Payload;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Chat extends Parent {

    private final int NAME_SERVER_PORT = 10000;
    private final String currentNickname;
    private final String currentPin;
    private final VBox mainVBox;
    private final TextField remoteNickname;
    private final TextField remoteMessage;
    private final Button sendMessageBtn;
    private static final ListView<Label> listView = new ListView<>();

    private final ChatController chatController;

    public Chat(String pin, String currentNickname) {
        this.currentNickname = currentNickname;
        this.currentPin = pin;
        chatController = new ChatController(pin);
        remoteNickname = new TextField();
        remoteMessage = new TextField();
        sendMessageBtn = new Button("Send message");
        setSendMessageButtonAction();
        mainVBox = new VBox(remoteNickname, remoteMessage, sendMessageBtn, listView);
    }

    public void setSendMessageButtonAction() {
        sendMessageBtn.setOnAction(event -> {
            String nickname = remoteNickname.getText();
            String message = remoteMessage.getText();
            chatController.sendMessage(null,"getByName " + nickname, NAME_SERVER_PORT);
            String peerPort = chatController.getPeerPort();
            chatController.sendMessage(currentNickname, message, Integer.parseInt(peerPort));
            updateListView(new Payload(currentNickname, currentPin, message));
        });
    }

    public static void updateListView(Payload payload) {
        Platform.runLater(() -> {
            Label label = new Label(payload.getNickname() + ": " + payload.getMessage());
            listView.getItems().add(label);
        });
    }

    public VBox getMainVBox() {
        return mainVBox;
    }
}
