/**
 * Created by linda on 3/12/2017.
 */
public class Header {
    private byte[] theResponse;
    private int pointer;

    private int queryID;                  // this is for the response it must match the one in the request
    private int answerCount = 0;          // number of answers
    private boolean decoded = false;      // Was this response successfully decoded
    private int nsCount = 0;              // number of nscount response records
    private int additionalCount = 0;      // number of additional (alternate) response records
    private boolean authoritative = false;// Is this an authoritative record
    private int questionCount = 0;

    public Header(byte[] theResponse){
        this.theResponse = theResponse;
        this.queryID = Utils.bitwise(0, 2);
        this.questionCount = Utils.bitwise(4, 2);
        this.answerCount = Utils.bitwise(6, 2);
        this.nsCount = Utils.bitwise(8, 2);
        this.additionalCount = Utils.bitwise(10, 2);

        this.pointer = 12;
    }

    public int getQueryID(){
        return queryID;
    }

    public int getQDCount(){
        return questionCount;
    }

    /*
        an unsigned 16 bit integer specifying the number of
        resource records in the answer section.
     */
    public int getANCount(){
        return answerCount;
    }

    /*
        an unsigned 16 bit integer specifying the number of name
        server resource records in the authority records section.
     */
    public int getNsCount(){
        return nsCount;
    }

    /*
        an unsigned 16 bit integer specifying the number of
        resource records in the additional records section.
     */
    public int getARCount(){
        return additionalCount;
    }


    public int getPointer(){
        return this.pointer;
    }
}
