package com.registousers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int WIDTH = 360;
    private static final int HEIGHT = 450;

    public static void main(String[] args)  {
        //new UserController().runServer();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Button btn = new Button("button");
        StackPane stack = new StackPane();
        stack.getChildren().addAll(btn);

        Scene scene = new Scene(stack, WIDTH, HEIGHT );
        stage.setTitle("Registo de Utilizadores");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
