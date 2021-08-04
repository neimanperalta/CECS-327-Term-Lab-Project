import java.io.*;
import java.net.*;

public class Server {
    
    public static void main(String args[] ) throws UnknownHostException, IOException
    {
        
        //Socket that will be used for the server to communicate
        ServerSocket serverSocket = new ServerSocket(1234);

        //Attempts to establish a connection and waits for the client
        Socket clientSocket = serverSocket.accept();

        //Reads in the input that was recieved from the client server
        DataInputStream din = new DataInputStream(clientSocket.getInputStream() );

        //Storing what was written from the client connection
        String string = (String) din.readUTF();

        //Printing that the message has been recieved
        System.out.println("message= " + string);

        //Closing of the socket
        serverSocket.close();

    }//Close of Main Runner

}//End of Server class
