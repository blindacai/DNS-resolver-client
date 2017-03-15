import java.io.IOException;

/**
 * Created by linda on 3/4/2017.
 */
public class testMain {
    public static final String DNS = "198.162.35.1";
    public static final int MAX_LEN = 1024;
    public static String FQDN = "www.cs.ubc.ca";

    public static void main(String[] args) throws IOException {

        Datagram datagram = new Datagram(DNS, FQDN);
        DNSResponse dnsResponse = new DNSResponse();


//        System.out.println(Utils.byteLookup(89));
//        System.out.println(Utils.getRDataNS(89, 6));
//        System.out.println(Utils.byteLookup(answer.getPointer()));
    }
}