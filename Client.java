import java.net.*;
import java.io.*;

public class Client {
    public static void main (String[]args) throws UnknownHostException, IOException {


        System.out.println("Waiting for connection...");

        //Client socket
        while (true) {
            ServerSocket serverSocket = new ServerSocket(8000);
            new clientThread(serverSocket.accept()).start();
            serverSocket.close();
            Socket clientSocket = new Socket(InetAddress.getLocalHost(), 8001);
            new clientThread(clientSocket);
            clientSocket.close();
        }
    }
}

class clientThread extends Thread{
    private Socket sock = null;
    private InetAddress ip;
    private int port = 0;

    public clientThread(Socket sock) {
        this.sock = sock;
    }

    public clientThread(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            System.out.println("connection has been made!");
            if (port !=0) {
                System.out.println(ip + " has connected");
            }
            //do sync 
            sock.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}