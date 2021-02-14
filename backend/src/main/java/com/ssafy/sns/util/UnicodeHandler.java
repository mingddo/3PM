package com.ssafy.sns.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UnicodeHandler {

    private final static String[] CHO = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
    private final static String[] JOONG = {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"};
    private final static String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};

    public String splitHangeulToConsonant(String text) {

        StringBuilder result = new StringBuilder();

        for(int i = 0; i < text.length(); i++) {
            char uniVal = text.charAt(i);
             if(uniVal >= 0xAC00) {
                 uniVal = (char)(uniVal - 0xAC00);
                 char cho = (char)(uniVal / 28 / 21);
                 char joong = (char) (uniVal / 28 % 21);
                 char jong = (char) (uniVal % 28);
                 result.append(CHO[cho]).append(JOONG[joong]).append(JONG[jong]);
             } else {
                 result.append(uniVal);
             }
        }

        return result.toString();
    }
}
