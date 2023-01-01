package com.registousers;

import com.registousers.controller.UserController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;
    private static final UserController userController = new UserController();
    private static TextField nicknameTextField;
    private static TextField pinTextField;
    private static Label serverResponseLabel;

    public static void main(String[] args)  {
        new Thread(new Thread(){
            public void run() {
                userController.runServer();
            }
        }).start();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Label nicknameLabel = new Label("Nickname:");
        nicknameTextField = new TextField();
        HBox horBox1 = new HBox();
        horBox1.setSpacing(10);
        horBox1.setAlignment(Pos.BASELINE_CENTER);
        horBox1.setPadding(new Insets(10, 10, 10, 10));
        horBox1.getChildren().addAll(nicknameLabel, nicknameTextField);

        Label pinLabel = new Label("Pin:");
        pinTextField = new TextField();
        HBox horBox2 = new HBox();
        horBox2.setSpacing(10);
        horBox2.setAlignment(Pos.BASELINE_CENTER);
        horBox2.setPadding(new Insets(10, 10, 10, 10));
        horBox2.getChildren().addAll(pinLabel, pinTextField);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(horBox1, horBox2);

        Button registerBtn = new Button("Register");
        Button rememberPin = new Button("Remember Pin");
        setButtonsActions(registerBtn, rememberPin);
        VBox vBoxBtns = new VBox();
        vBoxBtns.setSpacing(5);
        vBoxBtns.setAlignment(Pos.BASELINE_CENTER);
        vBoxBtns.setPadding(new Insets(10, 10, 10, 10));
        vBoxBtns.getChildren().addAll(registerBtn, rememberPin);

        serverResponseLabel = new Label("");
        serverResponseLabel.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox();
        mainVBox.getChildren().addAll(vBox, serverResponseLabel, vBoxBtns);

        Scene scene = new Scene(mainVBox, WIDTH, HEIGHT );
        stage.setTitle("Registo de Utilizadores");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setButtonsActions(Button registerBtn, Button remeberPinBtn) {
        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nickname = nicknameTextField.getText();
                String pin = pinTextField.getText();
                userController.sendRequestToNameService("set " + nickname + " " + pin);
                getServerResponse();
            }
        });

        remeberPinBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nickname = nicknameTextField.getText();
                userController.sendRequestToNameService("get " + nickname);
                getServerResponse();
            }
        });

    }

    private static void getServerResponse() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String serverResponse = userController.getLastReceivedMessage();
        serverResponseLabel.setText("Server response: " + serverResponse);
    }

}
