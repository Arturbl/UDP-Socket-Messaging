package com.trocamensagagens.view;


import com.trocamensagagens.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Login extends Parent {

    private Stage primaryStage;
    private Label serverResponseLabel;
    private TextField pinTextField;
    private final VBox vBox = new VBox();

    public  Login(Stage primaryStage) {

        this.primaryStage = primaryStage;

        Label pinLabel = new Label("Pin:");
        pinTextField = new TextField();
        HBox horBox2 = new HBox();
        horBox2.setSpacing(20);
        horBox2.setAlignment(Pos.BASELINE_CENTER);
        horBox2.setPadding(new Insets(10, 10, 10, 10));
        horBox2.getChildren().addAll(pinLabel, pinTextField);

        Button loginBtn = new Button("Login");
        setButtonsActions(loginBtn);
        loginBtn.setAlignment(Pos.CENTER);
        loginBtn.setPadding(new Insets(10));

        Label l1 = new Label("Server response: ");
        serverResponseLabel = new Label("");
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(l1, serverResponseLabel);

        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().addAll(horBox2, loginBtn, hBox);
    }

    public void setButtonsActions(Button loginbtn)  {
        try {
            loginbtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String pin = pinTextField.getText();
                    String response = new LoginController().sendRequestForNickname(pin);
                    System.out.println(response);
                    serverResponseLabel.setText(response);
                    if (!Objects.equals(response, "false")) {
                        changeScene(response);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeScene(String nickname) {
        Chat chat = new Chat(nickname, pinTextField.getText());
        Scene scene = new Scene(chat.getRoot(), 300, 200);
        primaryStage.setScene(scene);
    }

    public VBox getVBox() {
        return vBox;
    }
}

