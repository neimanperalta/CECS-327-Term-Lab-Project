import java.net.*;
import java.io.*;

public class Client {
    public static void main (String[]args) throws UnknownHostException, IOException {
        
        //Broadcast to network
        System.out.println("");


        //Create listen socket
        Socket hostSock = new Socket("localhost", 32721);
        DataInputStream in = new DataInputStream(in);

        //Attempt at having one socket do commands, and another socket that gives input from those commands
        Socket writing = new Socket("localhost",32722);


        //close socket
        hostSock.close();
    
    }
}