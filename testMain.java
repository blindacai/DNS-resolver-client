import java.io.IOException;

/**
 * Created by linda on 3/4/2017.
 */
public class testMain {
    //public static final String PATH = "C:\\Users\\Alex\\Desktop\\CS317\\New folder (2)\\DNSInitialQuery.bin";
    public static final String PATH = "D:\\UBC\\2016 term 2\\cpsc 317\\assignment\\a2\\DNSInitialQuery.bin";
    public static final String DNS = "198.162.35.1";
    public static final int MAX_LEN = 1024;
    public static String FQDN = "www.cs.ubc.ca";
    //public static String FQDN = "www.stanford.edu";

    //private static int curPointer;

    public static void main(String[] args) throws IOException {
//        DatagramSocket clientSocket = new DatagramSocket();
//        InetAddress address = InetAddress.getByName(DNS);
//
//        DeEncodeQuery encoder = new DeEncodeQuery();
//
//        byte[] DNSquery = encoder.enCodeFQDN(FQDN);
//
//
////        Path path = Paths.get(PATH);
////        byte[] buf = Files.readAllBytes(path);  // to be replaced by encoded user input
//
//        DatagramPacket packet = new DatagramPacket(DNSquery, DNSquery.length, address, 53);
//        clientSocket.send(packet);
//
//        // to receive a response
//        byte[] buftwo = new byte[MAX_LEN];
//        packet = new DatagramPacket(buftwo, buftwo.length);
//        clientSocket.receive(packet);
//
//        byte[] response = packet.getData();
//        DNSResponse_org dns_response = new DNSResponse_org(response);
//        Utils.setReponse(response);

        Datagram datagram = new Datagram(DNS, FQDN);
        byte[] response = datagram.getReponse();

        int pointer_pos;

        Header header = new Header(response);
        System.out.println("Response ID: " + header.getQueryID());

        // Queries
        QuerySection qs = new QuerySection(response);
        pointer_pos = qs.getPointer();

        // may not be false if contains cname
        // Answers
        System.out.println("Answers: " + header.getANCount());
        for(int i = 0; i < header.getANCount(); i++){
            ResourceRecord answer = new ResourceRecord(response, pointer_pos, false);
            Utils.toPrint(answer);
            pointer_pos = answer.getPointer();
        }

        // Authoritative
        System.out.println("Nameservers: " + header.getNsCount());
        for(int i = 0; i < header.getNsCount(); i++){
            ResourceRecord nameServer = new ResourceRecord(response, pointer_pos, true);
            Utils.toPrint(nameServer);
            pointer_pos = nameServer.getPointer();
        }

        // Additional
        System.out.println("Additional: " + header.getARCount());
        for(int i = 0; i < header.getARCount(); i++){
            ResourceRecord additional = new ResourceRecord(response, pointer_pos, false);
            Utils.toPrint(additional);
            pointer_pos = additional.getPointer();
        }


//        System.out.println(Utils.byteLookup(89));
//        System.out.println(Utils.getRDataNS(89, 6));
//        System.out.println(Utils.byteLookup(answer.getPointer()));
    }
}


// should we look up for IP of NS in additional info section?
// should we exhaust all NS, if we couldn't find IP of some of them?
// what's the purpose of boolean field: uthoritative
// what's the purpose of boolean field: decoded
// cname: authoritative true, either there is an IP or is the cname