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
        return (first_byte & 0xc0) == 0xc0;
    }
}
