import java.net.*;
import java.io.*;

public class Client {

    private static DatagramSocket broadSock = null;
    private static String clientHostName = null;
    private static InetAddress clientAddress = null;

    public static void main (String[]args) throws UnknownHostException, IOException {
               
        //Client socket
        Socket clientSocket = new Socket("localhost",1234);

        //Broadcast a Packet to Network for discovery
        clientHostName = InetAddress.getLocalHost().getHostName();
        clientAddress = InetAddress.getLocalHost();

        broadcast(clientHostName, clientAddress); 

        //Reads in what the server types from the socket
        DataInputStream din = new DataInputStream(clientSocket.getInputStream() );

        //Outputs what the server types from the socket
        DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream() );

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

    // Function to Broadcast the clients hostname and local address to the network
    public static void broadcast(String nodeName, InetAddress address) throws IOException {
        broadSock = new DatagramSocket();
        broadSock.setBroadcast(true);
        
        byte[] buf = nodeName.getBytes();
        DatagramPacket p = new DatagramPacket(buf, buf.length, address, 12121);
        broadSock.send(p);
        broadSock.close();
    }

}//End of Client class