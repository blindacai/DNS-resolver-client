/**
 * Created by linda on 3/12/2017.
 */
public class QuestionSection {
    private int head = 12;
    private byte[] theResponse;

    private String QName;

    public QuestionSection(byte[] theResponse){
        this.theResponse = theResponse;
        this.QName = Utils.getName(theResponse, head);
    }

    public String getQName(){
        return QName;
    }

    public int getPointer(){
        this.head += this.QName.length();
        this.head += 4;   // get over Qtype and Qclass

        return this.head;
    }
}

