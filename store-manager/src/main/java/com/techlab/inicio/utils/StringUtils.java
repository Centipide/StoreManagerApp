package com.techlab.inicio.utils;

public class StringUtils {
    public static String titleCase(String text){
        String lowerCase = text.toLowerCase().trim();
        String[] words = lowerCase.split(" ");

        StringBuilder normalized = new StringBuilder();
        for (String word: words)
        {
            if (!word.isEmpty())
            {
                normalized.append(Character.toUpperCase(word.charAt(0)));
                normalized.append(word.substring(1));
                normalized.append(" ");
            }
        }

        if (!normalized.isEmpty()) {
            normalized.deleteCharAt(normalized.length() - 1);
        }

        return normalized.toString();
    }

    public static  String normalizeKey(String key){
        return key.toLowerCase().trim();
    }
}
