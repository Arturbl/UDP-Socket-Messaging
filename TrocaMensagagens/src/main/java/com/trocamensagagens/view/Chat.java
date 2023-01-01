package com.trocamensagagens.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Chat extends Parent {

    String nickname;
    String pin;
    private VBox root;

    public Chat(String nickname, String pin) {
        this.nickname = nickname;
        this.pin = pin;

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        Button button = new Button("Send message");
        ListView<Label> listView = new ListView<>();

        button.setOnAction(event -> {
            String text1 = textField1.getText();
            String text2 = textField2.getText();
            Label label = new Label(text1 + " " + text2);
            listView.getItems().add(label);
        });

        root = new VBox(textField1, textField2, button, listView);

    }


    public VBox getRoot() {
        return root;
    }
}
