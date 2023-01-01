package com.trocamensagagens.view;


import com.trocamensagagens.controller.LoginController;
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

public class Login extends Application {

    private final int WIDTH = 300;
    private final int HEIGHT = 200;
    private Label serverResponseLabel;
    private TextField pinTextField;


    public void start(String[] args)  {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

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

        serverResponseLabel = new Label("");
        serverResponseLabel.setPadding(new Insets(10));
        serverResponseLabel.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().addAll(horBox2, loginBtn, serverResponseLabel);

        Scene scene = new Scene(vBox, WIDTH, HEIGHT );
        stage.setTitle("Troca de mensagens");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void setButtonsActions(Button loginbtn) throws Exception {
        loginbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String pin = pinTextField.getText();
                String response = new LoginController().sendRequestForNickname(pin);
                serverResponseLabel.setText("Server response: " + response);
            }
        });
    }

}

