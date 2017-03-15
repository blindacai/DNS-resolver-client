import java.io.IOException;

/**
 * Created by linda on 3/4/2017.
 */
public class testMain {
    public static final String DNS = "199.7.83.42";
    public static final int MAX_LEN = 1024;
    public static String FQDN = "www.cs.ubc.ca";

    public static void main(String[] args) throws IOException {
        int query_count = 1;

        Datagram datagram = new Datagram(DNS, FQDN);
        DNSResponse dnsResponse = new DNSResponse();

        while(!dnsResponse.getHeader().isAuthoritative()){
            query_count += 1;
            String first_NS = dnsResponse.getAuthos().get(0).getRRRdata();
            String child_DNS = dnsResponse.additionalLookup(first_NS);
            Datagram new_datagram = new Datagram(child_DNS, FQDN);
            dnsResponse = new DNSResponse();
        }



//        System.out.println(Utils.byteLookup(89));
//        System.out.println(Utils.getRDataNS(89, 6));
//        System.out.println(Utils.byteLookup(answer.getPointer()));
    }
}