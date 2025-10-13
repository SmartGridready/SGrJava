package com.smartgridready.communicator.common.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;

/**
 * Provides Regular expression helper methods.
 */
public class RegexHelper {

    /** Helper class. */
    private RegexHelper() {}

    /**
     * Matches a string.
     * @param regex the regular expression
     * @param str the input string
     * @return true if matches, false otherwise
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * Extracts a substring using a regular expression.
     * @param regex the regular expression
     * @param str the input string
     * @return an instance of {@link StringValue}
     */
    public static Value query(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return StringValue.of(matcher.find() ? matcher.group() : "");
    }
}
