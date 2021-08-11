import java.net.*;
import java.util.Enumeration;
import java.io.*;

public class Client {
    public static void main (String[]args) throws UnknownHostException, IOException {

        ServerSocket serverSocket = new ServerSocket(8000);
        Socket clientSocket = new Socket(InetAddress.getLocalHost(), 8000);

        try {
            Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces();
            while (nics.hasMoreElements()) {
                NetworkInterface nic = nics.nextElement();
                Enumeration<InetAddress> addrs = nic.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    System.out.printf("%s %s%n", nic.getName(),
                            addr.getHostAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("Waiting for connection...");

        //Client socket
        while (true) {
            new clientThread(serverSocket.accept()).start();
            System.out.println(clientSocket);
            new clientThread(clientSocket);
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