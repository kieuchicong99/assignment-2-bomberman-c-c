package uet.oop.bomberman.server;

import uet.oop.bomberman.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    public static void start(StringBuilder result, Game game) {
        ServerSocket serverSocket = null;
        int port=1410;
        while (true) {
            try {
                serverSocket = new ServerSocket(port);
                game.setPort(port);
                break;
            } catch (Exception e) {
                port++;
            }
        }

        try {
            System.out.println("Waiting for client connect...");
            DataInputStream dis = null;
            DataOutputStream dos = null;
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                String string = dis.readUTF();

//                System.out.println(string);
                result.delete(0, result.length());
                result.append(string);

                dos.flush();
                dos.close();
                dis.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
