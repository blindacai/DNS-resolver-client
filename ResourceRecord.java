import javax.annotation.Resource;
import javax.rmi.CORBA.Util;

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
        RRpointer++;
        if((data[RRpointer] & 0xc0) == 0xc0){
            // message is compressed
            int upperMostOffset = data[RRpointer] & 0x3f;
            upperMostOffset = upperMostOffset << 8;
            RRpointer++;
            int pointerLocation = data[RRpointer] | (byte)upperMostOffset;
            System.out.println("compFQDN index: " + pointerLocation);
            RRname = Utils.getName(data, pointerLocation);
            System.out.println("Name: " + RRname);
        } else {
            System.out.println("didn't make it in");
            System.out.println(RRpointer);
        }
    }

    public void setRRtype(){
        RRpointer++;
        this.RRtype = Utils.QTypelookup(Utils.bitwise(data, RRpointer, RRpointer+1));
        System.out.println("Type: " + RRtype);
        RRpointer++;

    }

    public void setRRclass(){
        RRpointer++;
        this.RRclass = Utils.QClasslookup(Utils.bitwise(data, RRpointer, RRpointer+1));
        System.out.println("Class: " + RRclass);
        RRpointer++;
    }

    public void setRRTTL(){
        RRpointer++;
        int upperBits = Utils.bitwise(data, RRpointer, RRpointer+1) << 8;
        RRpointer+=2;
        RRTTL = upperBits | Utils.bitwise(data, RRpointer, RRpointer+1);
        System.out.println("TTL: " + RRTTL);
        RRpointer++;
    }

    public void setRRRDlength(){
        RRpointer++;
        this.RRRDlength = Utils.bitwise(data, RRpointer, RRpointer+1);
        System.out.println("RDlength: " + RRRDlength);
        RRpointer++;
    }

    public void setRRRdata(){
        RRpointer++;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<RRRDlength; i++){
            sb.append(Utils.singlebitwise(data, RRpointer));
            sb.append(".");
            RRpointer++;
        }
        sb.deleteCharAt(sb.length()-1);
        RRRdata = sb.toString();
        System.out.println("Address: " + RRRdata);
        RRpointer--;
    }
}
