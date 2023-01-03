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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
        remoteNickname.setPromptText("Nickname");
        remoteMessage = new TextField();
        remoteMessage.setPromptText("Write a message");
        sendMessageBtn = new Button("Send message");
        setSendMessageButtonAction();
        mainVBox = new VBox(remoteNickname, remoteMessage, sendMessageBtn, listView);
    }

    public void setSendMessageButtonAction() {
        sendMessageBtn.setOnAction(event -> {
            String toSendNicknames = remoteNickname.getText();
            String message = remoteMessage.getText();
            if(!toSendNicknames.isEmpty() && !message.isEmpty()) {
                String[] nicknames = toSendNicknames.split(" ");
                Payload payload = null;
                for (String nickname : nicknames) {
                    chatController.sendMessage(null,"getByName " + nickname, NAME_SERVER_PORT);
                    String peerPort = chatController.getPeerPort();
                    if(!Objects.equals(peerPort, "false")) {
                        chatController.sendMessage(currentNickname, message, Integer.parseInt(peerPort));
                        payload = new Payload(currentNickname, currentPin, message);
                    }
                }
                updateListView(payload);
            }
        });
    }

    public static void updateListView(Payload payload) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String info = formatter.format(date) + " " + payload.getNickname() + ": " + payload.getMessage();
        Label label = new Label(info);
        Platform.runLater(() -> {
            listView.getItems().add(label);
        });
    }

    public VBox getMainVBox() {
        return mainVBox;
    }
}
