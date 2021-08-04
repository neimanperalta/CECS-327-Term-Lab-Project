import java.net.*;
import java.io.*;

public class Client {
    public static void main (String[]args) throws UnknownHostException, IOException {
        

        Socket clientSocket = new Socket("localhost",1234);

        DataOutputStream dout = new DataOutputStream( clientSocket.getOutputStream() );

        //Writing to the client server
        dout.writeUTF("HELLO PLEASE WORK ;C");

        //Forces bytes to be written to the stream
        dout.flush();

        //Closing of the data output stream
        dout.close();

        //Closing of the client socket
        clientSocket.close();

    }//End of main runner

}//End of Client class