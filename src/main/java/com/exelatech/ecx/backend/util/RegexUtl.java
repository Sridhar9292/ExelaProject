package com.exelatech.ecx.backend.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtl {
      /**
     * This method return all matches on a string
     *
     * @param textToExtract Text to extract
     * @param String[]      the pattern that will be used to extract the data
     * @return a converted Date object
     */
    public static ArrayList<String> extractDataFromString(String textToExtract, String[] regexPattern,
            String[] removeString, String excludeStrings[]) {
        ArrayList<String> matches = new ArrayList<String>();
        if (textToExtract == null || textToExtract.equals(""))
            return new ArrayList<String>();
        for (int i = 0; i < regexPattern.length; i++) {
            Pattern p = Pattern.compile(regexPattern[i]);
            Matcher m = p.matcher(textToExtract);
            while (m.find()) {
                String file = m.group();
                if (file != null && file.length() > 0) {
                    for (int k = 0; k < removeString.length; k++) {
                        file = file.replaceAll(removeString[k], "").trim();
                    }
                    for (int k = 0; k < excludeStrings.length; k++) {
                        file = file.contains(excludeStrings[k]) ? "" : file;
                    }
                    if (file.length() > 0) {
                        matches.add(file);

                    
                    }
                }
            }
        }
       
        return matches;
    }

    public RegexUtl() {
    }
    
}