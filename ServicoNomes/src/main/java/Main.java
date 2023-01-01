import controller.UserController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final int NAME_SERVER_PORT = 10000;
    private static final byte[] buffer = new byte[256];
    private static DatagramSocket socket;


    public static void main(String[] args) {
        try {
            socket = new DatagramSocket(NAME_SERVER_PORT);
            listenToRequests(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listenToRequests(byte[] buffer) throws IOException {
        System.out.println("[+] SERVIÇO DE NOMES A OUVIR NO PORTO: " + NAME_SERVER_PORT + "...");
        while(true) {
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivedPacket);
            String message = new String(receivedPacket.getData(),0, receivedPacket.getLength());
            System.out.println(message);
            List<String> command = Arrays.asList(message.split(" "));
            selectAction(receivedPacket, command);
        }
    }

    public static void selectAction(DatagramPacket receivedPacket, List<String> command) throws IOException {
        String message = getMessage(command);
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        DatagramPacket toSendSocket = new DatagramPacket(message.getBytes(), message.length(), ip, receivedPacket.getPort());
        socket.send(toSendSocket);
    }

    private static String getMessage(List<String> command) {
        List<String> info = command.subList(1, command.size()); // [register, artur, 8001] -> [artur, 8001]
        String message = "";
        if (Objects.equals(command.get(0), "set")) {
            boolean registered = UserController.registerNewUser(info.get(0), info.get(1));
            message = Boolean.toString(registered);
        } else if (Objects.equals(command.get(0), "get")) {
            message = UserController.getPin(info.get(0));
            message = message == null ? "false" : message; // check if message is null, bacause datagramSocket cannot send null params
        }
        return message;
    }

}
