import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.io.*;

public class Client {

    // private static DatagramSocket broadSock = null;
    // private static String clientHostName = null;
    // private static InetAddress clientAddress = null;
    static List<ClientHandler> clientList = new ArrayList<>();

    public static void main (String[]args) throws UnknownHostException, IOException {
        
        ServerSocket serverSock = new ServerSocket(8888);
        Socket clientSock;
        int clientCount = 0;

        while (true) {
            clientSock = serverSock.accept();
            System.out.println("New client request received : " + clientSock);

            // obtain input and output streams
            // InputStream fis = new InputStream();
            // DataOutputStream dout = new DataOutputStream(clientSock.getOutputStream());

            // BufferedInputStream bis = new BufferedInputStream(fis);

                
            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            ClientHandler clientObj = new ClientHandler(clientSock,"client " + clientCount);

            // Create a new Thread with this object.
            Thread clientThread = new Thread(clientObj);
                
            System.out.println("Adding this client to active client list");
    
            // add this client to active clients list
            clientList.add(clientObj);
    
            // start the thread.
            clientThread.start();
    
            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            clientCount++;
        }
        
    }//End of main runner    

}//End of Client class

class ClientHandler implements Runnable {
    
    static List<File> loF = new ArrayList<>();
    private String name;
    Socket s;
    // boolean isloggedin;
      
    // constructor
    public ClientHandler(Socket s, String name) {
        this.name = name;
        this.s = s;
        // this.isloggedin=true;
    }
  
    @Override
    public void run() {
  
        // String received;
        // List<String> fileDirLocal;
        // // create a list of ther files (by byter?)
        // // compare fileDirIn with fileDirLocal by iterating through list to compare by size
        // // if byte size is different, check timestamps (most recent should overwrite old file)
        // // optional : run byte check after file transfer to confirm file dir has the same contents
        // // close client thread
        // List<String> fileDirIn;
        // while (true) {

        //     try {
        //         // receive the string
        //         received = din.readUTF();         
                    
        //         System.out.println(received);
                    
        //         if(received.equals("logout")) { //if eof()
        //             this.isloggedin=false;
        //             break;
        //         }

        // Creating a File object for directory
        File directoryPath = new File("C:\\temp\\TermProject");
        File filesList[] = directoryPath.listFiles();
        for (File file : filesList){
            loF.add(file);
        }

        for (File file : loF) {
            // List of all files and directories
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream buffIn = new BufferedInputStream(fis);
                byte[] buffer = new byte[(int) file.length()];

                buffIn.read(buffer, 0, buffer.length);
                OutputStream out = s.getOutputStream();
                out.flush();
                buffIn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}


                    
        //         // break the string into message and recipient part
        //         StringTokenizer st = new StringTokenizer(received, "#");
        //         String MsgToSend = st.nextToken();
        //         String recipient = st.nextToken();

        //         // search for the recipient in the connected devices list.
        //         // ar is the vector storing client of active users
        //         for (ClientHandler mc : Client.clientList) 
        //         {
        //             // if the recipient is found, write on its
        //             // output stream
        //             if (mc.name.equals(recipient) && mc.isloggedin==true) 
        //             {
        //                 mc.dout.writeUTF(this.name+" : "+MsgToSend);
        //                 break;
        //             }
        //         }
        //     }  catch(Exception e) {
        //         System.out.println(e);
        //     }
        // }

        // try {
        //     // closing resources
        //     this.din.close();
        //     this.dout.close();
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
//     }
// }
