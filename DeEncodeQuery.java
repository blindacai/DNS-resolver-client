import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Alex on 2017-03-05.
 */
public class DeEncodeQuery {
    // Common starting place for all question queries (minus the QID)
    private byte[] queryHeaders = new byte[]{
            (byte) 0x00,                                        // QR
            (byte) 0x00,                                        // RCODE
            (byte) 0x00, (byte) 0x01,                           // QDCOUNT
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, // ANCOUNT
            (byte) 0x00, (byte) 0x00                            // ARCOUNT
    };

    // Seems to have common QTYPE, QCLASS for query questions
    private byte[] queryQTYPEQCLASS = new byte[] {
            (byte) 0x00, (byte) 0x01,                           // QTYPE
            (byte) 0x00, (byte) 0x01                            // QCLASS
    };

    // Create random byte array of size two for QID
    public byte[] getQID(){
        byte[] randomBytes = new byte[2];
        new Random().nextBytes(randomBytes);
        return randomBytes;
    }

    // Requires: Takes a FQDN to some website (Ex: www.ugrad.cs.ubc.ca)
    // Effects: replaces FQDN into byte form with "." replaced with length of next label
    //          (Ex: 0x03<www as bytes>0x05<ugrad as bytes>0x2<cs as bytes>
    public byte[] enCodeFQDN(String fqdn) throws IOException {
        ByteArrayOutputStream outputQuery = new ByteArrayOutputStream();
        String[] mySplitQname = fqdn.split("\\.");

        for(String label: mySplitQname) {
            outputQuery.write((byte)label.length());
            outputQuery.write(label.getBytes());
        }
        outputQuery.write((byte)0x00); // END OF QNAME
        byte[] QNAME = outputQuery.toByteArray();

        return queryAssembler(QNAME);
    }

    // Requires: A FQDN with "."'s replaced with label counts
    // Effects: Combines all query headers and FQDN into a single query for a Java datagram
    public byte[] queryAssembler(byte[] qname){
        byte[] QID = getQID();

        byte[] DNSquery = new byte[QID.length + qname.length + queryHeaders.length + queryQTYPEQCLASS.length];

        System.arraycopy(QID, 0, DNSquery, 0, QID.length);
        System.arraycopy(queryHeaders, 0, DNSquery, QID.length, queryHeaders.length);
        System.arraycopy(qname, 0, DNSquery, QID.length + queryHeaders.length, qname.length);
        System.arraycopy(queryQTYPEQCLASS, 0, DNSquery, QID.length + queryHeaders.length + qname.length,
                queryQTYPEQCLASS.length);

        return DNSquery;
    }

}
