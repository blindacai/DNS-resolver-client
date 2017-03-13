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
        //setRRclass();
//        this.RRclass =;
//        this.RRTTL =;
//        this.RRRDlength =;
//        this.RRRdata =;
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
            RRpointer++;
        }
    }

    public void setRRtype(){
        this.RRtype = Utils.QTypelookup(Utils.bitwise(data, RRpointer, RRpointer+1));
        System.out.println("Class: " + RRtype);
    }





}
