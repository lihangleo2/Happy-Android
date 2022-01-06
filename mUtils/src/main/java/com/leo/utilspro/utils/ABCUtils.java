package com.leo.utilspro.utils;

/**
 * Created by leo
 * on 2020/7/30.
 */
public class ABCUtils {
    //将小写字母 转换成大写字母
    public static String switchBig(String str){
        char[] c = str.toCharArray();
        for (int i = 0; i <str.length() ; i++) {
//                if (c[i] >= 'A' && c[i] <= 'Z')
//                    c[i] += 32;
//                else
                    if (c[i] >= 'a' && c[i] <= 'z')
                    c[i] -= 32;
        }

        return new String(c);
    }
}
