import java.net.*;
import java.io.*;

public class Client {
    public static void main (String[]args) throws UnknownHostException, IOException {
        
        //Broadcast to network
        System.out.println("");


        //Create listen socket
        Socket hostSock = new Socket("localhost", 32721);
        DataInputStream in = new DataInputStream(in);


        //close socket
        hostSock.close();
    
    }
}