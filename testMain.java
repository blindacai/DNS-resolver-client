import java.io.IOException;

/**
 * Created by linda on 3/4/2017.
 */
public class testMain {
    public static final String rootDNS = "199.7.83.42";
    public static String DNS = "199.7.83.42";
    public static final int MAX_LEN = 1024;
    public static String FQDN = "prep.ai.mit.edu";
    public static DNSResponse dnsResponse;

    public static void main(String[] args) throws IOException {
        int query_count = 1;
        boolean findAnswer = false;

        while(!findAnswer){
            Datagram datagram = new Datagram(DNS, FQDN);
            dnsResponse = new DNSResponse();
            dnsResponse.printAll();

            boolean autho = dnsResponse.getHeader().isAuthoritative();
            if(autho){
                String answer_type = dnsResponse.getAnswers().get(0).getRRtype();

                if(answer_type.equals("A") || answer_type.equals("AAAA")){
                    findAnswer = true;
                    break;
                }
                else{
                    FQDN = dnsResponse.getAnswers().get(0).getRRRdata();
                    DNS = rootDNS;
                }
            }
            else{
                String first_NS = dnsResponse.getAuthos().get(0).getRRRdata();
                String child_DNS = dnsResponse.additionalLookup(first_NS);
                if(child_DNS != null){
                    DNS = child_DNS;
                }
            }
        }
    }
}