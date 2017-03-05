import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by linda on 3/4/2017.
 */
public class testMain {
    public static final String PATH = "D:\\UBC\\2016 term 2\\cpsc 317\\assignment\\a2\\DNSInitialQuery.bin";
    public static final String DNS = "198.162.35.1";

    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(DNS);

        Path path = Paths.get(PATH);
        byte[] buf = Files.readAllBytes(path);  // to be replaced by encoded user input

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 53);
        clientSocket.send(packet);
    }
}
