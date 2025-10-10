package com.smartgridready.utils;

/**
 * Implements utility methods for strings.
 */
public class StringUtil {
    
    private StringUtil() {}

    /**
     * Tells if a string is not empty.
     * Returns {@code false} when the object is null.
     * @param str the string to check
     * @return a boolean
     */
    public static boolean isNotEmpty(String str) {
        return (str != null) && !str.isEmpty();
    }

    /**
     * Gets a string or an empty string if the given string is null.
     * @param str the string to get
     * @return a string
     */
    public static String getOrEmpty(String str) {
        return (str != null) ? str : "";
    }
}
