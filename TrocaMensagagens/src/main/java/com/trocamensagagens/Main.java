package com.trocamensagagens;

import com.trocamensagagens.view.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Login login = new Login(stage);

        Scene scene = new Scene(login.getVBox(), 300, 200);
        stage.setTitle("Chat");
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }
}
