/**
 * Created by Alex on 2017-03-12.
 */
public class NameServerResourceRecord extends ResourceRecord {
    public NameServerResourceRecord(byte[] data, int pointer) {
        super(data, pointer);
    }

    @Override
    public void setRRRdata() {
        RRpointer++;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<RRRDlength; i++){
            if((data[RRpointer] & 0xc0) == 0xc0){
                RRpointer++;
                sb.append(".");
                sb.append(Utils.getName(data, data[RRpointer]));
                RRpointer++;
                break;

            } else {
                RRpointer++;
                sb.append(Utils.byteToChar(data, RRpointer-3));
                RRpointer++;
            }
        }
        RRRdata = sb.toString();
        RRpointer--;
        System.out.println("Address: " + RRRdata);

    }
}
