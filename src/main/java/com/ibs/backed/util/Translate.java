package com.ibs.backed.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 翻译
 */
public class Translate {


    public static boolean hasMatch(String rule,String target){
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static String replaceString(String rule,String target,String replace){
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(target);
        return target.replaceAll(matcher.group(),replace);
    }

    public static String getString(String rule,String target){
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(target);
        if(matcher.find()) {
            return matcher.group();
        }
        return null;
    }


}
