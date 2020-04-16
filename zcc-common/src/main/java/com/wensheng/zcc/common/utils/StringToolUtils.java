package com.wensheng.zcc.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToolUtils {

    /*
        unicode编码范围：
        汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
        数字：[0x30,0x39]（或十进制[48, 57]）
        小写字母：[0x61,0x7a]（或十进制[97, 122]）
        大写字母：[0x41,0x5a]（或十进制[65, 90]）

     */
    public static boolean isNormalString(String name)
    {
        int n = 0;
        for(int i = 0; i < name.length(); i++) {
            n = (int)name.charAt(i);
            if(!(19968 <= n && n <40869) && !(48 <= n && n <= 57 )
                && !(97 <= n && n <= 122 ) && !(65 <= n && n <= 90 )
            ) {
                return false;
            }
        }
        return true;

    }

//    public static int countWord(String baseStr, String checkStr){
//        return Pattern.compile(String.format("\\b%s", checkStr)
//                .splitAsStream(baseStr).count()-1;
//    }

}
