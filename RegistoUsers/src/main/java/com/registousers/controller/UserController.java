package com.registousers.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UserController extends Thread {

    private final int NAME_SERVICE_SERVER_PORT = 10000;
    private final int USER_REGISTRATION_PORT = 10001;
    private final byte[] buffer = new byte[256];
    private DatagramSocket socket;
    private String lastReceivedMessage;

    public void runServer() {
        System.out.println("[+] REGISTO DE UTILIZADORES A CORRER NA PORTA: " + USER_REGISTRATION_PORT);
        try {
            socket = new DatagramSocket(USER_REGISTRATION_PORT);
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while(true) {
            try {
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivedPacket);
                String message = new String(receivedPacket.getData(),0, receivedPacket.getLength());
                lastReceivedMessage = message;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void sendRequestToNameService(String message) {
        try {
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, NAME_SERVICE_SERVER_PORT);
            socket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastReceivedMessage() {
        return lastReceivedMessage;
    }
}
