/**
 * Created by Alex on 2017-03-12.
 */
public class ResourceRecord {
    private String RRname;
    private String RRtype;
    private String RRclass;
    private int RRTTL;
    private int RRRDlength;
    private String RRRdata;
    private int RRpointer;

    private byte[] data;

    public ResourceRecord(byte[] data, int pointer){
        this.data = data;
        this.RRpointer = pointer + 1;
        setRRname();
        setRRtype();
        setRRclass();
        setRRTTL();
        setRRRDlength();
        setRRRdata();
    }

    public int getPointer(){
        return RRpointer;
    }

    // how to move forward pointer
    public void setRRname(){
        this.RRname = Utils.getRRName(RRpointer);
        RRpointer += 2;
    }

    public void setRRtype(){
        this.RRtype = Utils.QTypelookup(Utils.bitwise(RRpointer, 2));
        RRpointer += 2;
    }

    public void setRRclass(){
        this.RRclass = Utils.QClasslookup(Utils.bitwise(RRpointer, 2));
        RRpointer += 2;
    }

    public void setRRTTL(){
        this.RRTTL = Utils.bitwise(RRpointer, 4);
        RRpointer += 4;
    }

    public void setRRRDlength(){
        this.RRRDlength = Utils.bitwise(RRpointer, 2);
        RRpointer += 2;
    }

    public void setRRRdata(){
        this.RRRdata = Utils.byteToChar(RRpointer);
        RRpointer += RRRDlength;
    }


    public String getRRname() {
        return RRname;
    }

    public String getRRtype() {
        return RRtype;
    }

    public String getRRclass() {
        return RRclass;
    }

    public int getRRTTL() {
        return RRTTL;
    }

    public int getRRRDlength() {
        return RRRDlength;
    }

    public String getRRRdata() {
        return RRRdata;
    }

    public int getRRpointer() {
        return RRpointer;
    }

    public byte[] getData() {
        return data;
    }
}