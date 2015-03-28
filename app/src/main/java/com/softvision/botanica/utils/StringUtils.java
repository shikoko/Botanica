package com.softvision.botanica.utils;

public class StringUtils {
    public static final String EMPTY_STRING = "";

    public static boolean hasContent(String str) {
        if (str != null) {
            String newStr = str.trim();
            return !newStr.isEmpty();
        } else {
            return false;
        }
    }

    public static boolean hasContent(String str1, String str2) {
        if (str1 != null && str2 != null) {
            String newStr1 = str1.trim();
            String newStr2 = str2.trim();
            return !newStr1.isEmpty() && !newStr2.isEmpty();
        } else {
            return false;
        }
    }

    public static boolean equalsNull(String str1, String str2) {
        return (((str1 == null) && (str2 == null)) || str1.equals(str2));
    }

    public static boolean equalsNotNull(String str1, String str2) {
        return (str1 != null && str2 != null && str1.equals(str2));
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 != null && str2 != null && str1.equals(str2)) {
            return true;
        } else {
            return false;
        }
    }
}
