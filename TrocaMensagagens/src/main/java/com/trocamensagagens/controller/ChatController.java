package com.trocamensagagens.controller;

import com.trocamensagagens.model.Payload;
import com.trocamensagagens.view.Chat;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.*;

public class ChatController extends Thread {

    private String pin;
    private final byte[] buffer = new byte[256];
    private DatagramSocket socket;
    private String peerPort;

    public ChatController(String pin) {
        this.pin = pin;
        try {
            socket = new DatagramSocket(Integer.parseInt(pin));
            this.start();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("Chat instance running on: " + pin);
        while(true) {
            try {
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivedPacket);
                try {
                    byte[] data = receivedPacket.getData();
                    ByteArrayInputStream bais = new ByteArrayInputStream(data);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Payload payload = (Payload) ois.readObject();
                    Chat.updateListView(payload);
                } catch (Exception e) {
                    peerPort = new String(receivedPacket.getData(),0, receivedPacket.getLength());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void sendMessage(String nickname, String message, int port) {
        try {
            byte[] payload = getPayload(nickname, message, port);
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            DatagramPacket dp;
            if (nickname != null) { // send message to peer
                dp = new DatagramPacket(payload, payload.length, ip, port);
            } else { // connect to name server
                dp = new DatagramPacket(message.getBytes(), message.length(), ip, port);
            }
            socket.send(dp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getPayload(String nickname, String message, int port) {
        byte[] data;
        Payload payload = new Payload(nickname, Integer.toString(port), message);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(payload);
            oos.flush();
            data = baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public String getPeerPort() {
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {}
        return peerPort;
    }

}
