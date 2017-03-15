import java.util.ArrayList;
import java.util.List;

/**
 * Created by linda on 3/14/2017.
 */
public class DNSResponse {
    private int pointer_pos;
    private byte[] response;
    private Header header;
    private QuerySection qs;

    private List<ResourceRecord> answers;
    private List<ResourceRecord> authos;
    private List<ResourceRecord> additionals;

    public DNSResponse(){
        this.response = Utils.getResponse();

        answers = new ArrayList<ResourceRecord>();
        authos = new ArrayList<ResourceRecord>();
        additionals = new ArrayList<ResourceRecord>();

        printHeader();
        getQuerySection();
        printAnswers();
        printAuthoritative();
        printAdditional();
    }

    public void printHeader(){
        header = new Header();
        System.out.println("Response ID: " + header.getQueryID());
    }

    public void getQuerySection(){
        qs = new QuerySection(response);
        pointer_pos = qs.getPointer();
    }

    public void printAnswers(){
        // may not be false if contains cname
        System.out.println("Answers: " + header.getANCount());
        for(int i = 0; i < header.getANCount(); i++){
            ResourceRecord answer = new ResourceRecord(response, pointer_pos, false);
            answers.add(answer);
            Utils.toPrint(answer);
            pointer_pos = answer.getPointer();
        }
    }

    public void printAuthoritative(){
        System.out.println("Nameservers: " + header.getNsCount());
        for(int i = 0; i < header.getNsCount(); i++){
            ResourceRecord nameServer = new ResourceRecord(response, pointer_pos, true);
            authos.add(nameServer);
            Utils.toPrint(nameServer);
            pointer_pos = nameServer.getPointer();
        }
    }

    public void printAdditional(){
        System.out.println("Additional: " + header.getARCount());
        for(int i = 0; i < header.getARCount(); i++){
            ResourceRecord additional = new ResourceRecord(response, pointer_pos, false);
            additionals.add(additional);
            Utils.toPrint(additional);
            pointer_pos = additional.getPointer();
        }
    }

    public List<ResourceRecord> getAnswers() {
        return answers;
    }

    public List<ResourceRecord> getAuthos() {
        return authos;
    }

    public List<ResourceRecord> getAdditionals() {
        return additionals;
    }

    public Header getHeader() {
        return header;
    }
}
