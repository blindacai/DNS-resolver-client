/**
 * Created by linda on 3/14/2017.
 */
public class DNSResponse {
    private int pointer_pos;
    private byte[] response;

    public DNSResponse(byte[] response){
        this.response = response;
    }

    public void printHeader(){

    }
}
