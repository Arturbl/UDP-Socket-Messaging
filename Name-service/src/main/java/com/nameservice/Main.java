package com.nameservice;


import com.nameservice.controller.UserController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Main extends Application {

    private static final int NAME_SERVER_PORT = 10000;
    private static final byte[] buffer = new byte[256];
    private static DatagramSocket socket;

    private static final ListView<Label> listView = new ListView<>();


    public static void main(String[] args) {
        launch();
    }

    // init message listener
    @Override
    public void start(Stage stage) throws Exception {
        initGUI(stage);
        try {
            socket = new DatagramSocket(NAME_SERVER_PORT);
            new Thread(new Thread(){
                public void run() {
                    try {
                        listenToRequests(buffer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initGUI(Stage stage) {
        Scene scene = new Scene(listView, 300, 200);
        stage.setTitle("Servico de Nomes");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void listenToRequests(byte[] buffer) throws IOException {
        System.out.println("[+] SERVIÇO DE NOMES A OUVIR NO PORTO: " + NAME_SERVER_PORT + "...");
        while(true) {
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivedPacket);
            String message = new String(receivedPacket.getData(),0, receivedPacket.getLength());
            System.out.println(message);
            updateListView(message);
            List<String> command = Arrays.asList(message.split(" "));
            selectAction(receivedPacket, command);
        }
    }

    public static void selectAction(DatagramPacket receivedPacket, List<String> command) throws IOException {
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        DatagramPacket toSendSocket;
        if( command.size() > 1 ) { // prevent sending null and crashing name server
            String message = getMessage(command);
            toSendSocket = new DatagramPacket(message.getBytes(), message.length(), ip, receivedPacket.getPort());
            socket.send(toSendSocket);
            return;
        }
        String error = "Insert valid data.";
        toSendSocket = new DatagramPacket(error.getBytes(), error.length(), ip, receivedPacket.getPort());
        socket.send(toSendSocket);
    }

    private static String getMessage(List<String> command) throws IOException {
        List<String> info = command.subList(1, command.size());
        String message = "";
        try {
            if (Objects.equals(command.get(0), "set")) {
                boolean registered = UserController.registerNewUser(info.get(0), info.get(info.size() - 1));
                message = Boolean.toString(registered);
            } else if (Objects.equals(command.get(0), "getByName")) {
                message = UserController.getPinByName(info.get(0));
                message = message == null ? "false" : message; // check if message is null, bacause datagramSocket cannot send null params
            } else if (Objects.equals(command.get(0), "getByPin")) {
                message = UserController.getNameByPin(info.get(0));
                message = message == null ? "false" : message; // check if message is null, bacause datagramSocket cannot send null params
            }
        } catch (Exception e) {}
        return message;
    }

    public static void updateListView(String message) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String info = formatter.format(date) + " " + message;
        Label label = new Label(info);
        Platform.runLater(() -> {
            listView.getItems().add(label);
        });
    }

}
