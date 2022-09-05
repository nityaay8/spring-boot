package com.n9.util;

import org.codehaus.jettison.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BatchUtil {

    private static final String regex
            = "\\b((?:https?|ftp|file):"
            + "//[-a-zA-Z0-9+&@#/%?="
            + "~_|!:, .;]*[-a-zA-Z0-9+"
            + "&@#/%=~_|])";

    public static final Pattern pattern = Pattern.compile(
            BatchUtil.regex,
            Pattern.CASE_INSENSITIVE);


    public static String extractUrl(String bodyTxt) throws Exception {
        String url = null;

        // Find the match between string
        // and the regular expression
        Matcher m = pattern.matcher(bodyTxt);

        // Find the next subsequence of
        // the input subsequence that
        // find the pattern
        if (m.find()) {

            // Find the substring from the
            // first index of match result
            // to the last index of match
            // result and add in the list
            url = bodyTxt.substring(
                    m.start(0), m.end(0));
        }

        return url;


    }

    public static String extractRawMessageDataUrl(String rawmessagedata) throws Exception {
        String url = null;

        JSONObject rawmessagedataObj = new JSONObject(rawmessagedata);

        String bodyTxt = rawmessagedataObj.getString("body");

        Matcher m = pattern.matcher(bodyTxt);

        if (m.find()) {

            url = bodyTxt.substring(
                    m.start(0), m.end(0));
        }

        return url;
    }
}
