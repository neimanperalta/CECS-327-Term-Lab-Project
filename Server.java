import java.io.*;
import java.net.*;

public class Server {
    
    public static void main(String args[] ) throws UnknownHostException, IOException
    {
        
        //Socket that will be used for the server to communicate
        ServerSocket serverSocket = new ServerSocket(8080);

        //Attempts to establish a connection and waits for the client
        Socket clientSocket = serverSocket.accept();

        //Reads in the input that was recieved from the client server
        DataInputStream din = new DataInputStream(clientSocket.getInputStream() );

        //Outputs information to the client
        DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream() );

        //Buffers the reading so that its legible
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in) );

        //Strings that will be used the store what has been said
        String string1 = "";
        String string2 = "";

        //Keep looping and looking for information until the client says to stop
        while(!string1.equals("stop") )
        {
            //Reads in the information the client is typing and store it to the string
            string1 = din.readUTF();

            //Output to the console that the client has said something
            System.out.println("Client says: " + string1);

            //Read in what the server wants to say and stores it to this string
            string2 = br.readLine();

            //Writes what was read in from the server side, and then will output it to the client socket side
            dout.writeUTF(string2);
            dout.flush();
        }

        //Closing of the data input stream
        din.close();
        
        //Closing of the reading socket because the client/server has typed in stop
        clientSocket.close();

        //Closing of the server socket since the client/server has exited
        serverSocket.close();

    }//Close of Main Runner

}//End of Server class
