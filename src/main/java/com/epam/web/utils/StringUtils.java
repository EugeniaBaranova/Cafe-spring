package com.epam.web.utils;

public class StringUtils {

    private static final String EMPTY = "";
    private static final String NUMERAL_STRING = "\\d+";

    public static boolean isEmpty(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String parameter){
        return !isEmpty(parameter);
    }

    public static String empty(){
        return EMPTY;
    }

    public static boolean isNumeral(String parameter) {
        return parameter.matches(NUMERAL_STRING);
    }
}
