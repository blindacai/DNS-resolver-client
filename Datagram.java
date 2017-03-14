import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by linda on 3/14/2017.
 */
public class Datagram {
    private String DNS;
    private String FQDN;
    private byte[] response;

    public Datagram(String DNS, String FQDN) throws IOException {
        this.DNS = DNS;
        this.FQDN = FQDN;
        this.getResponse();
    }

    public void getResponse() throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(DNS);

        // encode query
        DeEncodeQuery encoder = new DeEncodeQuery();
        byte[] DNSquery = encoder.enCodeFQDN(FQDN);

        // send a packet
        DatagramPacket packet = new DatagramPacket(DNSquery, DNSquery.length, address, 53);
        clientSocket.send(packet);

        // receive a packet
        byte[] buf = new byte[Utils.MAX_LEN];
        packet = new DatagramPacket(buf, buf.length);
        clientSocket.receive(packet);

        // create response
        this.response = packet.getData();
        Utils.setReponse(response);
    }

    public String getDNS() {
        return DNS;
    }

    public String getFQDN() {
        return FQDN;
    }

    public byte[] getReponse(){
        return this.response;
    }
}
