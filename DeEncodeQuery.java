import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Alex on 2017-03-05.
 */
public class DeEncodeQuery {
    // Todo: create QueryID

    // Common starting place for all question queries (minus the QID)
    private byte[] queryHeaders = new byte[] {
            (byte)0x00,                                     // QR
            (byte)0x00,                                     // RCODE
            (byte)0x00, (byte)0x01,                         // QDCOUNT
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // ANCOUNT
            (byte)0x00, (byte)0x00};                        // ARCOUNT

    // Requires: Takes a FQDN to some website (Ex: www.ugrad.cs.ubc.ca)
    // Effects: Returns the FQDN in byte form with "." replaced with length of next part
    //          (Ex: 03<www as bytes>05<ugrad as bytes>2<cs as bytes>
    public void enCodeFQDN(String fqdn) throws IOException {
        ByteArrayOutputStream outputQuery = new ByteArrayOutputStream();
        String[] mySplitQname = fqdn.split("\\.");

        for(String label: mySplitQname) {
            outputQuery.write((byte)label.length());
            outputQuery.write(label.getBytes());
        }
        outputQuery.write((byte)0x00); // END OF QNAME
        byte[] QNAME = outputQuery.toByteArray();

        queryAssembler(QNAME);
    }

    // Requires: A FQDN with "."'s replaced with label counts
    // Effects: Combines all query headers and FQDN into a single query for a Java datagram
    public void queryAssembler(byte[] qname){
        byte[] header = new byte[qname.length + queryHeaders.length];

        System.arraycopy(queryHeaders, 0, header, 0, queryHeaders.length);
        System.arraycopy(qname, 0, header, queryHeaders.length, qname.length);

        // header shall contain everything except QID, QTYPE and QCLASS
        // Todo: create QTYPE and QCLASS headers
    }

}
