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

    public void setRRname(){
        if(Utils.checkCompressed(data[RRpointer])){
            int pointerLocation = Utils.bitwise(RRpointer, 2) & 0x3fff;
            this.RRname = Utils.getName(pointerLocation);
        }else{
            System.out.println("didn't make it in");
            System.out.println(RRpointer);
        }
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
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < RRRDlength; i++){
            sb.append(Utils.bitwise(RRpointer + i, 1));
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        RRRdata = sb.toString();

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
