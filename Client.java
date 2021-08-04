import java.net.*;
import java.io.*;

public class Client {
    public static void main (String[]args) throws UnknownHostException, IOException {
        
        //Client socket
        Socket clientSocket = new Socket("localhost",1234);

        //Reads in what the server types from the socket
        DataInputStream din = new DataInputStream(clientSocket.getInputStream() );

        //Outputs what the server types from the socket
        DataOutputStream dout = new DataOutputStream( clientSocket.getOutputStream() );

        //BufferedReader to make things more legible
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in) );

        //Strings that will store what the client says back
        String string1 = "";
        String string2 = "";

        //Keep looping until the server says stop
        while(!string1.equals("stop") )
        {
            //Reads in what was inputted by client
            string1 = br.readLine();

            //Writes to the socket what was read in from string1, so the server side can read it
            dout.writeUTF(string1);

            dout.flush();

            //Reads what was written from the server side and stores it
            string2 = din.readUTF();

            //Outputs to the console what the server socket has said
            System.out.println("Server says: " + string2);
        }

        //Forces bytes to be written to the stream
        dout.flush();

        //Closing of the client socket
        clientSocket.close();

    }//End of main runner

}//End of Client class