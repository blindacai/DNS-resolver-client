/**
 * Created by linda on 3/12/2017.
 */
public class QuerySection {
    private int head = 12;
    private byte[] theResponse;

    private String QName;

    public QuerySection(byte[] theResponse){
        this.theResponse = theResponse;
        this.QName = Utils.getRRName(head);
    }

    public String getQName(){
        return QName;
    }

    public int getPointer(){
        this.head += this.QName.length();
        this.head += 1;   // get over 00
        this.head += 4;   // get over Qtype and Qclass
        this.head += 1;   // place it at the beginning of the next section

        return this.head;

    }
}

