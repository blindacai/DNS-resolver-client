import java.io.IOException;
import java.net.*;

/**
 * Created by linda on 3/4/2017.
 */
public class testMain {

    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("199.7.83.42");
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 53);
        clientSocket.send(packet);
    }
}
