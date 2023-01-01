package com.trocamensagagens.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class LoginController {

    private DatagramSocket socket;
    private final byte[] buffer = new byte[256];

    public String sendRequestForNickname(String pin) {
        String serverResponse = "";
        String message = "getByPin " + pin;
        try {
            socket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            int NAME_SERVICE_SERVER_PORT = 10000;
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, NAME_SERVICE_SERVER_PORT);
            socket.send(dp);
            serverResponse = listenToServerResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }

    public String listenToServerResponse() {
        String response = "";
        while(response.isEmpty()) {
            try {
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivedPacket);
                response = new String(receivedPacket.getData(),0, receivedPacket.getLength());
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return response;
    }
}
