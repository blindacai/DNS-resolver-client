import javax.annotation.Resource;

/**
 * Created by Alex on 2017-03-12.
 */
public class ResourceRecord {
    String RRname;
    String RRtype;
    String RRclass;
    int RRTTL;
    int RRRDlength;
    String RRRdata;
    int RRpointer;
    byte[] data;

    public ResourceRecord(byte[] data, int pointer){
        this.data = data;
        this.RRpointer = pointer;
        this.RRname =;
        this.RRtype =;
        this.RRclass =;
        this.RRTTL =;
        this.RRRDlength =;
        this.RRRdata =;
    }


}
