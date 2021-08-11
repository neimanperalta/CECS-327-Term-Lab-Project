import java.net.*;
import java.io.*;

public class Client {

    public static void main (String[]args) throws UnknownHostException, IOException {




        System.out.println("Starting...");

        new ClientThread().start();
        new ServerThread().start();

    }
}


class ServerThread extends Thread {

    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;

    public ServerThread() {}


    @Override
    public void run() {
        // System.out.println("Server Thread is running. waiting for connection...");
        try {
            //Socket that will be used for the server to communicate
            ServerSocket serverSocket = new ServerSocket(8000);

            //Attempts to establish a connection and waits for the client
            System.out.println("Listening on port 8000");
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
        } catch (IOException ex) {
            System.out.println(ex);
        } 
    }
}

class ClientThread extends Thread {

    InetAddress addr;
    String ip;

    public ClientThread() {
    }


    @Override
    public void run() {
        // System.out.println("Client Thread is running. Waiting for connection...");
        try {
            try(final DatagramSocket socket = new DatagramSocket()){
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
                addr = InetAddress.getByName(ip);
              }
                    //Client socket
        System.out.println(ip + " attemption to connect on port 8000");
        
        Socket clientSocket = new Socket(addr, 8000, addr, 8080);
        System.out.println(clientSocket);

        //Reads in what the server types from the socket
        DataInputStream din = new DataInputStream(clientSocket.getInputStream() );

        //Outputs what the server types from the socket
        DataOutputStream dout = new DataOutputStream( clientSocket.getOutputStream() );

        //Writing to the client server
        // dout.writeUTF("HELLO PLEASE WORK ;C");
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

        //Closing of the data output stream
        dout.close();

        //Closing of the client socket
        clientSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

