import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by linda on 3/4/2017.
 */
public class testMain {
    //public static final String PATH = "C:\\Users\\Alex\\Desktop\\CS317\\New folder (2)\\DNSInitialQuery.bin";
    public static final String PATH = "D:\\UBC\\2016 term 2\\cpsc 317\\assignment\\a2\\DNSInitialQuery.bin";
    public static final String DNS = "198.162.35.1";
    public static final int MAX_LEN = 1024;
    public static String FQDN = "www.cs.ubc.ca";

    private static int curPointer;

    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(DNS);

        DeEncodeQuery decoder = new DeEncodeQuery();

        byte[] DNSquery = decoder.enCodeFQDN(FQDN);


//        Path path = Paths.get(PATH);
//        byte[] buf = Files.readAllBytes(path);  // to be replaced by encoded user input

        DatagramPacket packet = new DatagramPacket(DNSquery, DNSquery.length, address, 53);
        clientSocket.send(packet);

        // to receive a response
        byte[] buftwo = new byte[MAX_LEN];
        packet = new DatagramPacket(buftwo, buftwo.length);
        clientSocket.receive(packet);

        byte[] response = packet.getData();
        DNSResponse dns_response = new DNSResponse(response);
        Utils.setReponse(response);

        Header header = new Header(response);
        System.out.println("Response ID: " + header.getQueryID());

        // Queries
        QuerySection qs = new QuerySection(response);

        // Answers
        System.out.println("Answers: " + header.getANCount());
        ResourceRecord answer = new ResourceRecord(response, qs.getPointer(), false);
        //System.out.println("data: " + answer.getRRRdata());
        System.out.format(" %-30s %-10d %-4s %s\n", answer.getRRname(), answer.getRRTTL(), answer.getRRtype(), answer.getRRRdata());


        // Authoritative
        System.out.println("Nameservers: " + header.getNsCount());
        ResourceRecord ns_one = new ResourceRecord(response, answer.getPointer(), true);
        System.out.format(" %-30s %-10d %-4s %s\n", ns_one.getRRname(), ns_one.getRRTTL(), ns_one.getRRtype(), ns_one.getRRRdata());
        ResourceRecord ns_two = new ResourceRecord(response, ns_one.getPointer(), true);
        System.out.format(" %-30s %-10d %-4s %s\n", ns_two.getRRname(), ns_two.getRRTTL(), ns_two.getRRtype(), ns_two.getRRRdata());

        // Additional
        System.out.println("Additional: " + header.getARCount());
        ResourceRecord add_one = new ResourceRecord(response, ns_two.getPointer(), false);
        System.out.format(" %-30s %-10d %-4s %s\n", add_one.getRRname(), add_one.getRRTTL(), add_one.getRRtype(), add_one.getRRRdata());
        ResourceRecord add_two = new ResourceRecord(response, add_one.getPointer(), false);
        System.out.format(" %-30s %-10d %-4s %s\n", add_two.getRRname(), add_two.getRRTTL(), add_two.getRRtype(), add_two.getRRRdata());



//        System.out.println(Utils.byteLookup(89));
//        System.out.println(Utils.getRDataNS(89, 6));
//        System.out.println(Utils.byteLookup(answer.getPointer()));
    }
}