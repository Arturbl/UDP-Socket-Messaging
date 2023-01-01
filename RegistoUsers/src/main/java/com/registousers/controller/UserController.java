package com.registousers.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UserController extends Thread {

    private static final int NAME_SERVICE_SERVER_PORT = 10000;
    private static final int USER_REGISTRATION_PORT = 10001;
    private static final byte[] buffer = new byte[256];
    private static DatagramSocket socket;



    public void runServer() {
        System.out.println("[+] REGISTO DE UTILIZADORES A CORRER NA PORTA: " + USER_REGISTRATION_PORT);
        try {
            socket = new DatagramSocket(USER_REGISTRATION_PORT);
            this.start();
            listenToRequests();
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
                System.out.println(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void listenToRequests() throws IOException {
        while(true) {
            String obj = new Scanner(System.in).nextLine();
            sendRequestToNameService(obj);
        }
    }

    public static void sendRequestToNameService(String message) {
        try {
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, NAME_SERVICE_SERVER_PORT);
            socket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
