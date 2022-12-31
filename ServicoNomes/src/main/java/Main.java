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
            System.out.println("[+] SERVIÇO DE NOMES A OUVIR NO PORTO: " + NAME_SERVER_PORT + "...");
            listenToRequests(socket, buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listenToRequests(DatagramSocket socket, byte[] buffer) throws IOException {
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
        List<String> info = command.subList(1, command.size()); // [register, artur, 8001] -> [artur, 8001]
        String message = "";
        if (Objects.equals(command.get(0), "register")) {
            boolean registered = UserController.registerNewUser(info.get(0), info.get(1));
            message = Boolean.toString(registered);
        } else if (Objects.equals(command.get(0), "get")) {
            message = UserController.getPin(info.get(0));
        }
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        DatagramPacket toSendSocket = new DatagramPacket(message.getBytes(), message.length(), ip, receivedPacket.getPort());
        socket.send(toSendSocket);
    }


}
