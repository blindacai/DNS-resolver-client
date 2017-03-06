import java.io.ByteArrayInputStream;
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
    public static final String PATH = "C:\\Users\\Alex\\Desktop\\CS317\\New folder (2)\\DNSInitialQuery.bin";
    public static final String DNS = "198.162.35.1";
    public static final int MAX_LEN = 512;
    public static String FQDN = "www.ugrad.cs.ubc.ca";

    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(DNS);

        DeEncodeQuery decoder = new DeEncodeQuery();

        decoder.enCodeFQDN(FQDN);


        Path path = Paths.get(PATH);
        byte[] buf = Files.readAllBytes(path);  // to be replaced by encoded user input

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 53);
        clientSocket.send(packet);

        // to receive a response
        byte[] buftwo = new byte[MAX_LEN];
        packet = new DatagramPacket(buftwo, buftwo.length);
        clientSocket.receive(packet);

        byte[] response = packet.getData();
        ByteArrayInputStream readbyte = new ByteArrayInputStream(response);
        int reader;
        while((reader = readbyte.read())!= -1){
            //System.out.println(Integer.toHexString(reader));
        }
    }
}