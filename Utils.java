/**
 * Created by linda on 3/12/2017.
 */
public class Utils {
    private static byte[] theResponse;

    public static void setReponse(byte[] response){
        theResponse = response;
    }

    public static String QTypelookup(int type){
        switch(type){
            case 1:
                return "A";
            case 28:
                return "AAAA";
        }
        return null;
    }


    public static String QClasslookup(int type){
        switch(type){
            case 1:
                return "IN";
        }
        return null;
    }

    public static String getName(int pointer){
        String result = "";

        int localhead = pointer;

        while(theResponse[localhead] != 0){
            if(localhead != pointer)
                result += ".";

            result += byteToChar(localhead);
            localhead += theResponse[localhead] + 1;
        }
        return result;
    }

    // convert byte to ASCII
    public static String byteToChar(int pointer){
        String result = "";
        for(int i = 1; i <= theResponse[pointer]; i++){
            result += (char)theResponse[pointer + i];
        }
        return result;
    }

    // convert FFFF to unsigned int
    public static int bitwise(int pos_first, int pos_second){
        return ((theResponse[pos_first] & 0xff) << 8) + (theResponse[pos_second] & 0xff);
    }

    public static int singlebitwise(byte[] theResponse, int pos_first){
        return (theResponse[pos_first] & 255);
    }
}
