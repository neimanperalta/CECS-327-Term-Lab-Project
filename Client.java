import java.net.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.*;

public class Client {

    private static DatagramSocket broadSock = null;
    private static String clientHostName = null;
    private static InetAddress clientAddress = null;

    public static void main (String[]args) throws UnknownHostException, IOException {
               
        //Client socket
        Socket clientSocket = new Socket("localhost",8080);

        //Retrieve the host name and address of client
        clientHostName = InetAddress.getLocalHost().getHostName();
        clientAddress = InetAddress.getByName("localhost");

        //Broadcast a Packet to Network for discovery
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

    //Function to Broadcast the clients hostname and local address to the network
    public static void broadcast(String nodeName, InetAddress address) throws IOException {
        //create a socket to broadcast from and enables it to broadcast
        broadSock = new DatagramSocket();
        broadSock.setBroadcast(true);
        
        //create buffer for the incoming packet
        byte[] buf = (nodeName + " is broadcasting").getBytes();

        //load packet with the data and broadcasts to the network
        DatagramPacket p = new DatagramPacket(buf, buf.length, address, 12121);
        
        //send_it.gif
        broadSock.send(p);
        broadSock.close();
    }

    //Establish a list of Network Interfaces currently on the network
    List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        //instantiate list InetAddresses
        List<InetAddress> broadcastList = new ArrayList<>();

        //an enumerated list that contains hostname IP of interfaces
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
    
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
    
            networkInterface.getInterfaceAddresses().stream()
            .filter(address -> address.getBroadcast() != null)
            .map(address -> address.getBroadcast())
            .forEach(broadcastList::add);
        }
        return broadcastList;
    }

}//End of Client class