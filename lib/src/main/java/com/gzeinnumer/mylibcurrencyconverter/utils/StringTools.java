package com.gzeinnumer.mylibcurrencyconverter.utils;

public class StringTools {

    public static String trimCommaOfString(String value){
        if (value.contains(",")) {
            return value.replace(",", "");
        } else {
            return value;
        }
    }

    public static String trimCommaOfString(String string, String srefix){
        if (string.contains(",") || srefix.length()>0) {
            if (srefix.length()>0)
                return string.replace(",", "").replace(srefix,"");
            else
                return string.replace(",", "");
        } else {
            return string;
        }
    }
}
