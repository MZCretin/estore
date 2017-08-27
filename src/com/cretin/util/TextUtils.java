package com.cretin.util;

public class TextUtils {
    public static boolean isEmpty(String text){
        if(text == null || text == ""){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isEmpty(Character text){
        if(text == null || text.toString() == ""){
            return true;
        }else {
            return false;
        }
    }
}
