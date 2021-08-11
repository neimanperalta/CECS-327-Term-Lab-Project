import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;

public class Send {
    public static void main(String[] args) throws IOException {
        String directory = "c:/temp/toSend/";
        String hostDomain = "127.0.0.1";
        int port = 1234;

        File[] files = new File(directory).listFiles();

        Socket socket = new Socket(InetAddress.getByName(hostDomain), port);

        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt(files.length);

        for(File file:files)
        {
            long length = file.length();
            dos.writeLong(length);
            

            String name = file.getName();
            dos.writeUTF(name);

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            int theByte = 0;
            while ((theByte = bis.read()) != -1)
                bos.write(theByte);

            bis.close();
        }

        dos.close();
    }
}
