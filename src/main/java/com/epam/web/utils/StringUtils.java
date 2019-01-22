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

    public static boolean isDigit(String digit){
        if(digit != null){
            char[] chars = digit.toCharArray();
            if(chars[0] != 46 && chars[0] <=57){
                for(int i = 0; i < chars.length; i++){
                    if(chars[i] != 46){
                        if(chars[i] < 48 || chars[i] > 57){
                            return false;
                        }
                    }
                }
                return true;
            }
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
