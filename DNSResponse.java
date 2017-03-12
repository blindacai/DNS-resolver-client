import java.net.InetAddress;

// Lots of the action associated with handling a DNS query is processing
// the response. Although not required you might find the following skeleton of
// a DNSreponse helpful. The class below has bunch of instance data that typically needs to be 
// parsed from the response. If you decide to use this class keep in mind that it is just a 
// suggestion and feel free to add or delete methods to better suit your implementation as 
// well as instance variables.



public class DNSResponse {
    private int queryID;                  // this is for the response it must match the one in the request
    private int answerCount = 0;          // number of answers  
    private boolean decoded = false;      // Was this response successfully decoded
    private int nsCount = 0;              // number of nscount response records
    private int additionalCount = 0;      // number of additional (alternate) response records
    private boolean authoritative = false;// Is this an authoritative record


    private byte[] theResponse;

    // Note you will almost certainly need some additional instance variables.

    // todo: When in trace mode you probably want to dump out all the relevant information in a response

	void dumpResponse() {
		


	}

    // The constructor: you may want to add additional parameters, but the two shown are 
    // probably the minimum that you need.

	public DNSResponse (byte[] data, int len) {
        this.theResponse = data;
	    // The following are probably some of the things 
	    // you will need to do.
	    // Extract the query ID
        this.queryID = getQueryID();
	    // Make sure the message is a query response and determine
        if((data[2] & 0x80) != 1){ return; }               // ensure QR bit is 1 (response)
        // if it is an authoritative response or note
        if((data[2] * 0x04) == 1){ authoritative = true; } // check if AA bit set to 1 (authoritative)
        if((data[3] * 0xff) != 0){ return; }                // check if RCODE is 0 (no error in response code)

	    // determine answer count
        answerCount = getANCount();

	    // determine NS Count
        nsCount = getNsCount();

	    // determine additional record count
        additionalCount = getARCount();

	    // todo: Extract list of answers, name server, and additional information response
	    // records
	}

	public DNSResponse(byte[] data){
    }


    // todo: You will probably want a methods to extract a compressed FQDN, IP address
    // todo: cname, authoritative DNS servers and other values like the query ID etc.
    public String getCompressedFQDN(){
        return "";
    }

    public InetAddress getIPaddr(){
        return null;
    }

    public String getCNAME(){
        return "";
    }

    public String getAuthoritativeDNSservers(){
        return "";
    }

    public int getQueryID(){
        this.queryID = bitwise(0, 1);
        return queryID;
    }

    /*
        an unsigned 16 bit integer specifying the number of
        resource records in the answer section.
     */
    public int getANCount(){
        this.answerCount = bitwise(6, 7);
        return answerCount;
    }

    /*
        an unsigned 16 bit integer specifying the number of name
        server resource records in the authority records section.
     */
    public int getNsCount(){
        this.nsCount = bitwise(8, 9);
        return nsCount;
    }

    /*
        an unsigned 16 bit integer specifying the number of
        resource records in the additional records section.
     */
    public int getARCount(){
        this.additionalCount = bitwise(10, 11);
        return additionalCount;
    }

    // todo:
    // You will also want methods to extract the response records and record
    // the important values they are returning. Note that an IPV6 reponse record
    // is of type 28. It probably wouldn't hurt to have a response record class to hold
    // these records.


    // convert FFFF to unsigned int
    public int bitwise(int pos_first, int pos_second){
        return ((theResponse[pos_first] & 0xff) << 8) + (theResponse[pos_second] & 0xff);
    }
}


