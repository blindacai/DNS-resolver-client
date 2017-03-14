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

    private boolean nameServer;

    private byte[] response;

    public ResourceRecord(byte[] data, int pointer, boolean nameServer){
        this.response = data;
        this.RRpointer = pointer;
        this.nameServer = nameServer;
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

    public void setRRname(){
        this.RRname = Utils.getRRName(RRpointer);
        RRpointer += Utils.getNameLength(RRpointer);
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
        this.RRRdata = nameServer? Utils.getRDataNS(RRpointer, RRRDlength) : Utils.getRDataIP(RRRDlength, RRpointer);
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

    public byte[] getResponse() {
        return response;
    }
}