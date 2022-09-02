package com.n9.processor;

import com.n9.model.SavedMsgData;
import com.n9.model.TinyUrlData;
import com.n9.service.ResourceURLService;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SavedMsgProcessor implements ItemProcessor<SavedMsgData, SavedMsgData> {

    // Regular Expression to extract
    // URL from the string
    String regex
            = "\\b((?:https?|ftp|file):"
            + "//[-a-zA-Z0-9+&@#/%?="
            + "~_|!:, .;]*[-a-zA-Z0-9+"
            + "&@#/%=~_|])";

    Pattern pattern = null;


    private ResourceURLService resourceURLService;

    public SavedMsgProcessor(ResourceURLService resourceURLService) {
        // Compile the Regular Expression
        pattern = Pattern.compile(
                regex,
                Pattern.CASE_INSENSITIVE);
        this.resourceURLService = resourceURLService;
    }

    private static final Logger log = LoggerFactory.getLogger(SavedMsgProcessor.class);

    @Override
    public SavedMsgData process(final SavedMsgData savedMsgData) throws Exception {
        String savedmessageid = savedMsgData.getSavedmessageid();
        String body = savedMsgData.getBody();
        String rawmessagedata = savedMsgData.getRawmessagedata();

        String rawMsgUrl = extractRawMessageDataUrl(rawmessagedata);
        String updatedRawMsgUrl = resourceURLService.proccessUrl(rawMsgUrl);
        if (updatedRawMsgUrl != null) {
            rawmessagedata = rawmessagedata.replace(rawMsgUrl, updatedRawMsgUrl);
        }

        String bodyUrl = extractUrl(body);
        String updatedUrl = resourceURLService.proccessUrl(bodyUrl);
        if (updatedUrl != null) {
            body = body.replace(bodyUrl, updatedUrl);
        }

        SavedMsgData transformedSavedMsgData = new SavedMsgData();
        transformedSavedMsgData.setSavedmessageid(savedmessageid);
        transformedSavedMsgData.setBody(body);
        transformedSavedMsgData.setRawmessagedata(rawmessagedata);

        log.info("Converting (" + savedMsgData + ") into (" + transformedSavedMsgData + ")");

        return transformedSavedMsgData;
    }

    private String extractUrl(String bodyTxt) throws Exception {
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

    private String extractRawMessageDataUrl(String rawmessagedata) throws Exception {
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