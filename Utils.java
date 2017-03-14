/**
 * Created by linda on 3/12/2017.
 */
public class Utils {
    private static byte[] theResponse;
    public static final int MAX_LEN = 1024;

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
            case 5:
                return "CN";
            case 6:
                return "SOA";
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


    /*
        deal with multi pointers
     */
    public static String getName(int pointer, boolean inCompressed, int length){
        String result = "";
        int bytevalue = theResponse[pointer];

        if(theResponse[pointer] == 0){
            return "";
        }
        if(length == 0 && !inCompressed){
            return "";
        }
        else{
            if(Utils.checkCompressed(pointer)){
                return result += getName(extractPos(pointer), true, length - 2);
            }
            else{
                if(inCompressed){
                    return result += Utils.byteToChar(pointer) + "." + getName(pointer + bytevalue + 1, true, length);
                }
                else{
                    return result += Utils.byteToChar(pointer) + "." + getName(pointer + bytevalue + 1, false, length - bytevalue - 1);
                }
            }
        }
    }

    /*
        get Name section
     */
    public static String getRRName(int pointer){
        String result = getName(pointer, false, -1);
        return result.substring(0, result.length() - 1);
    }

    /*
        get RData section when type is NS
     */
    public static String getRDataNS(int pointer, int length){
        String result = getName(pointer, false, length);
        return result.substring(0, result.length() - 1);
    }

    /*
        get RData section when type is A or AAAA
     */
    public static String getRDataIP(int length, int pointer){
        String result = "";

        for(int i = 0; i < length; i++){
            result += (theResponse[pointer] & 0xff) + ".";
            pointer += 1;
        }
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
        get byte length of name section
     */
    public static int getNameLength(int pointer){
        int result = 0;

        while(theResponse[pointer] != 0){
            if(checkCompressed(pointer)){
                result += 2;
                return result;
            }
            else{
                result += 1;
                pointer += 1;
            }
        }
        return result;
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
        for testing purpose
     */
    public static String byteLookup(int position){
        return Integer.toHexString(theResponse[position] & 0xff);
    }

    public static void toPrint(ResourceRecord rr){
        System.out.format(" %-30s %-10d %-4s %s\n", rr.getRRname(), rr.getRRTTL(), rr.getRRtype(), rr.getRRRdata());
    }
}
