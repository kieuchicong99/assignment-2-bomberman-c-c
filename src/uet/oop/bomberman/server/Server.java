package uet.oop.bomberman.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
    public static void start(String result){
        try {
            ServerSocket serverSocket = new ServerSocket(1440);
            System.out.println("Waiting for client connect...");
            DataInputStream dis = null;
            DataOutputStream dos = null;
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                String string = dis.readUTF();

                System.out.println(string);


                dos.flush();
                dos.close();
                dis.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
