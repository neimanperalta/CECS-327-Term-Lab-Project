import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.net.InetAddress;
import java.net.ServerSocket;

public class ReceEdit {
    public static void main(String[] args) throws IOException {
        // the directory to scan for files
        String dirPath = "c:/temp/receive/";

        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = serverSocket.accept();

        // get the input from the sender socket
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        DataInputStream dis = new DataInputStream(bis);

        int filesCount = dis.readInt();
        File[] files = new File[filesCount];
        ///
        File fileDir = new File("C:\\temp\\receive\\");
        File fileList[] = fileDir.listFiles();
        int numFiles;
        if (fileList.length >= filesCount)
            numFiles = fileList.length;
        else
            numFiles = filesCount;
        ///

        // read each files and write it to the directory, =
        // condition checking for duplicate is implemented 
        // but timestamp/file size is not condsitered
        for(int i = 0;i<numFiles;i++)
        {
            long fileLength = dis.readLong();
            String fileName = dis.readUTF();

            FileOutputStream fos;
            BufferedOutputStream bos;
            if (fileList.length == 0) {
                // for(File f : fileList) {
                    // if (!f.getName().equals(fileName)) {
                        files[i] = new File(dirPath + "/" + fileName);
                        System.out.println(fileName + " transfer");
                        fos = new FileOutputStream(files[i]);
                        bos = new BufferedOutputStream(fos);
                        for (int j = 0; j < fileLength; j++)
                            bos.write(bis.read());
                        bos.close();
                    // }
                    // else {
                    // }
                    
                // }
            }
            else {
                for(File f : fileList) {
                    if (!f.getName().equals(fileName)) {
                        files[i] = new File(dirPath + "/" + fileName);
                        System.out.println(fileName + " transfer");
                        fos = new FileOutputStream(files[i]);
                        bos = new BufferedOutputStream(fos);
                        for (int j = 0; j < fileLength; j++)
                            bos.write(bis.read());
                        bos.close();
                    }
                    // else {
                    // }
                }
            }
            
            // files[i] = new File(dirPath + "/" + fileName);

            // FileOutputStream fos = new FileOutputStream(files[i]);
            // BufferedOutputStream bos = new BufferedOutputStream(fos);

            // for (int j = 0; j < fileLength; j++)
            //     bos.write(bis.read());

            // bos.close();
        }

        dis.close();
    }
}
