import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

// Lots of the action associated with handling a DNS query is processing
// the response. Although not required you might find the following skeleton of
// a DNSreponse helpful. The class below has bunch of instance data that typically needs to be 
// parsed from the response. If you decide to use this class keep in mind that it is just a 
// suggestion and feel free to add or delete methods to better suit your implementation as 
// well as instance variables.



public class DNSResponse {

    private int ttl;
    private String recordType;
    private String recordValue;

    private int head;      // position of the byte pointer
    private String compressedFQDNName = "";

    // Note you will almost certainly need some additional instance variables.

    // todo: When in trace mode you probably want to dump out all the relevant information in a response

	void dumpResponse() {
		


	}

    // The constructor: you may want to add additional parameters, but the two shown are 
    // probably the minimum that you need.



    // todo: You will probably want a methods to extract a compressed FQDN, IP address
    // todo: cname, authoritative DNS servers and other values like the query ID etc.

    public InetAddress getIPaddr(){
        return null;
    }

	public DNSResponse(byte[] data){
        //this.theResponse = data;
    }





    // You will probably want a methods to extract a compressed FQDN, IP address
    // cname, authoritative DNS servers and other values like the query ID etc.


    // You will also want methods to extract the response records and record
    // the important values they are returning. Note that an IPV6 reponse record
    // is of type 28. It probably wouldn't hurt to have a response record class to hold
    // these records.



//    public void formatOutput(){
//        System.out.format(" %-30s %-10d %-4s %s\n", recordName, ttl, recordType, recordValue);
//    }
}


