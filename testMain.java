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
    public static final int MAX_LEN = 512;
    public static String FQDN = "www.ugrad.cs.ubc.ca";

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

//        //System.out.println(dns_response.getANCount());
//
//        ByteArrayInputStream readbyte = new ByteArrayInputStream(response);
//        int reader;
//        while((reader = readbyte.read())!= -1){
//            System.out.print(Integer.toHexString(reader));
//        }
//
//        System.out.println('\n');
//        //System.out.println(dns_response.getRecordName());

        QuestionSection questionSection = new QuestionSection(response);

//        List<ResourceRecord> answerList = new ArrayList<>();
//        List<ResourceRecord> nameServerList = new ArrayList<>();
//        List<ResourceRecord> additionalList = new ArrayList<>();
//
//        Header myHeader = new Header(response);
//        curPointer = questionSection.getPointer();
//        // store answers
//        System.out.println("start answer");
//        for(int i = 0; i < myHeader.getANCount(); i++){
//            ResourceRecord resourceRecord = new ResourceRecord(response, curPointer);
//            curPointer = resourceRecord.getPointer();
//
//            answerList.add(resourceRecord);
//        }
//
//        System.out.println();
//        System.out.println("start NameServer");
//        for(int i = 0; i < myHeader.getNsCount(); i++){
//
//            ResourceRecord resourceRecord = new NameServerResourceRecord(response, curPointer);
//            curPointer = resourceRecord.getPointer();
//
//            nameServerList.add(resourceRecord);
//        }
//
//        System.out.println();
//        System.out.println("start additional");
//        System.out.println(response[65]);
//        System.out.println(response[66]);
//        for(int i = 0; i < myHeader.getARCount(); i++){
//            ResourceRecord resourceRecord = new ResourceRecord(response, curPointer);
//            curPointer = resourceRecord.getPointer();
//
//            additionalList.add(resourceRecord);
//        }

        Header header = new Header(response);
        System.out.println("Answer count: " + header.getANCount());

        QuestionSection qs = new QuestionSection(response);

        ResourceRecord rr = new ResourceRecord(response, qs.getPointer());
        System.out.format(" %-30s %-10d %-4s %s\n", rr.getRRname(), rr.getRRTTL(), rr.getRRtype(), rr.getRRRdata());
    }
}