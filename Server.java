import java.io.*;
import java.net.*;

public class Server {
    
    public static void main(String args[] ) throws UnknownHostException, IOException
    {
        
        //Socket that will be used for the server to communicate
        ServerSocket serverSocket = new ServerSocket(1234);

        //Attempts to establish a connection and waits for the client
        Socket clientSocket = serverSocket.accept();

        DataInputStream in = new DataInputStream(clientSocket.getInputStream() );

        String string = (String) in.readUTF();

        System.out.println("message= " + string);

        serverSocket.close();
    }

}
