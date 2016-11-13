package api.util;

public class StringUtil {

    public static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }

    public static boolean convertPortugueseYesOrNoToBoolean(String value){
        return value.equalsIgnoreCase("sim") ? true : false;
    }
}