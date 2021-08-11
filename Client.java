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
    BufferedOutputStream buffOS;
    BufferedInputStream buffIS; 
    DataOutputStream dataOS;
    DataInputStream dataIS;
    FileInputStream fileIS;
    FileOutputStream fileOS;
    Socket s;
      
    // constructor
    public ClientHandler(Socket s, String name) {
        this.name = name;
        this.s = s;
    }
  
    @Override
    public void run() {
        try {
        // // create a list of ther files (by byter?)
        // // compare fileDirIn with fileDirLocal by iterating through list to compare by size
        // // if byte size is different, check timestamps (most recent should overwrite old file)
        // // optional : run byte check after file transfer to confirm file dir has the same contents
        // // close client thread

        // Creating a File object for directory
        // File directoryPath = new File("C:\\temp\\TermProject");
        // File filesList[] = directoryPath.listFiles();
        // for (File file : filesList){
        //     loF.add(file);
        // }

        File[] files = new File("C:\\temp\\TermProject").listFiles();

        buffOS = new BufferedOutputStream(s.getOutputStream());
        dataOS = new DataOutputStream(buffOS);

        dataOS.writeInt(files.length);

        for(File file : files) {
            long length = file.length();
            dataOS.writeLong(length);

            String name = file.getName();
            dataOS.writeUTF(name);

            fileIS = new FileInputStream(file);
            buffIS = new BufferedInputStream(fileIS);

            int i = 0;
            while((i = buffIS.read()) != -1) buffOS.write(i);
            buffIS.close();
        }
        
    dataOS.close();

    buffIS = new BufferedInputStream(s.getInputStream());
    dataIS = new DataInputStream(buffIS);

    int filesCount = dataIS.readInt();
    File[] filesIN = new File[filesCount];

    for(int i = 0; i < filesCount; i++)
    {
        long fileLength = dataIS.readLong();
        String fileName = dataIS.readUTF();

        filesIN[i] = new File("C:\\temp\\TermProject\\TempFiles" + "/" + fileName);

        fileOS = new FileOutputStream(filesIN[i]);
        buffOS = new BufferedOutputStream(fileOS);

        for(int j = 0; j < fileLength; j++) buffOS.write(buffIS.read());

        buffOS.close();
    }

    dataIS.close();


    } catch (Exception ex) {
        System.out.println(ex);
    }
}

}
