
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SimpleFileClientCopy {

    public final static int SOCKET_PORT = 1234; // change this
    public final static String SERVER = "127.0.0.1"; // localhost
    public final static String FILE_TO_RECEIVED = "c:/temp/receive/testGot.txt"; // change this

    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                                 // should bigger than the file to be downloaded

    public static void main(String[] args) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");

            // receive file
            byte[] mybytearray = new byte[FILE_SIZE];
            InputStream is = sock.getInputStream();


            // is = new DataInputStream(is);
            // String fileName = is.readUTF();
            // OutputStream output = new FileOutputStream(fileName);
            // long size = is.read();
            // while (size > 0 && (bytesRead = in.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
            //     output.write(buffer, 0, bytesRead);
            //     size -= bytesRead;
            // }

            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;
            
            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0)
                    current += bytesRead;
            } while (bytesRead > -1);

            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
        } finally {
            if (fos != null)
                fos.close();
            if (bos != null)
                bos.close();
            if (sock != null)
                sock.close();
        }
    }

}