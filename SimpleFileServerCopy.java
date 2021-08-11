
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleFileServerCopy {

    public final static int SOCKET_PORT = 1234; // change this
    public final static String FILE_TO_SEND = "c:/temp/toSend/test.txt"; // change this

    public static void main(String[] args) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        File fileDir = new File("C:\\temp\\toSend\\");
        try {
            servsock = new ServerSocket(SOCKET_PORT);
            while (true) {
                System.out.println("Waiting...");
                try {
                    sock = servsock.accept();
                    System.out.println("Accepted connection : " + sock);
                    // send file
                    File fileList [] = fileDir.listFiles();
                    for(File file : fileList) {
                        File myFile = new File("C:\\temp\\toSend\\"+file.getName());
                        System.out.println(file.getName());
                        byte[] mybytearray = new byte[(int) myFile.length()];
                        fis = new FileInputStream(myFile);
                        bis = new BufferedInputStream(fis);
                        bis.read(mybytearray, 0, mybytearray.length);
                        os = sock.getOutputStream();
                        System.out.println("Sending " + "C:\\temp\\toSend\\"+ file.getName() + "(" + mybytearray.length + " bytes)");
                        os.write(mybytearray, 0, mybytearray.length);
                        os.flush();
                    }
                    // File myFile = new File(FILE_TO_SEND);
                    // byte[] mybytearray = new byte[(int) myFile.length()];
                    // fis = new FileInputStream(myFile);
                    // bis = new BufferedInputStream(fis);
                    // bis.read(mybytearray, 0, mybytearray.length);
                    // os = sock.getOutputStream();
                    // System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                    // os.write(mybytearray, 0, mybytearray.length);
                    // os.flush();
                    System.out.println("Done.");
                } finally {
                    if (bis != null)
                        bis.close();
                    if (os != null)
                        os.close();
                    if (sock != null)
                        sock.close();
                }
            }
        } finally {
            if (servsock != null)
                servsock.close();
        }
    }
}