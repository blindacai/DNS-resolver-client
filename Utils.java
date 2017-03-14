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
            case 2:
                return "NS";
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


    public static String getRRName(int pointer){
        String result = "";
        int bytevalue = theResponse[pointer];

        if(theResponse[pointer] == 0){
            return "";
        }
        else{
            if(Utils.checkCompressed(pointer)){
                return result += getRRName(Utils.extractPos(pointer));
            }
            else{
                return result += Utils.byteToChar(pointer) + "." + getRRName(pointer + bytevalue + 1);
            }
        }
    }

    public static String getName(int pointer){
        String result = getRRName(pointer);
        return result.substring(0, result.length() - 1);
    }

    // convert bytes to ASCII based on the value of theResponse[pointer]
    public static String byteToChar(int pointer){
        String result = "";
        for(int i = 1; i <= theResponse[pointer]; i++){
            result += (char)theResponse[pointer + i];
        }
        return result;
    }


    // convert any length of byte to insigned int
    public static int bitwise(int start_pos, int num){
        if(num == 1){
            return theResponse[start_pos] & 0xff;
        }
        else{
            return ((theResponse[start_pos] & 0xff) << (8 * (num - 1))) + bitwise(start_pos + 1, num - 1);
        }
    }

    /*
        check whether the high order 2 bit is 11
     */
    public static boolean checkCompressed(int first_byte){
        return (theResponse[first_byte] & 0xc0) == 0xc0;
    }

    /*
        extract position of the compressed message
     */
    public static int extractPos(int first_byte){
        return bitwise(first_byte, 2) & 0x3fff;
    }

    /*
        for testing
     */
    public static String byteLookup(int position){
        return Integer.toHexString(theResponse[position] & 0xff);
    }
}
