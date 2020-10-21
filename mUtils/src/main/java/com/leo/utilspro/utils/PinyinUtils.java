package com.leo.utilspro.utils;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: 李航
 * @description:
 * @projectName: SelectCityDome
 * @date: 2017-03-22
 * @time: 15:45
 */
public class PinyinUtils {
    /**
     * 获得汉语拼音首字母
     *
     * @param chines 汉字
     * @return
     */
    public static String getChineaseABC(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * 中文转拼音全拼,英文字符不变
     *
     * @param inputString 汉字
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        if (inputString != null && inputString.length() > 0
                && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                                input[i], format);
                        output += temp[0];
                    } else
                        output += Character.toString(input[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "*";
        }
        return output;
    }

    /**
     * 中文转汉语拼音首字母，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }


    /**
     * 将小写字母转成大写字母
     */

    //将小写字母 转换成大写字母
    public static String switchSmallToBig(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
//                if (c[i] >= 'A' && c[i] <= 'Z')
//                    c[i] += 32;
//                else
            if (c[i] >= 'a' && c[i] <= 'z')
                c[i] -= 32;
        }

        return new String(c);
    }

    //将小写字母 转换成大写字母
    public static String switchBigToSmall(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (c[i] >= 'A' && c[i] <= 'Z')
                c[i] += 32;
//                else
//            if (c[i] >= 'a' && c[i] <= 'z')
//                c[i] -= 32;
        }

        return new String(c);
    }


    //将大小写互相转换
    public static String switchLetter(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (c[i] >= 'A' && c[i] <= 'Z')
                c[i] += 32;
            else if (c[i] >= 'a' && c[i] <= 'z')
                c[i] -= 32;
        }

        return new String(c);
    }


}
