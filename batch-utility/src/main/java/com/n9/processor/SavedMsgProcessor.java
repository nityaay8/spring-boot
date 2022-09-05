package com.n9.processor;

import com.n9.model.SavedMsgData;
import com.n9.model.TinyUrlData;
import com.n9.service.ResourceURLService;
import com.n9.util.BatchUtil;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SavedMsgProcessor implements ItemProcessor<SavedMsgData, SavedMsgData> {

    // Regular Expression to extract
    // URL from the string


    private ResourceURLService resourceURLService;

    public SavedMsgProcessor(ResourceURLService resourceURLService) {
        // Compile the Regular Expression

        this.resourceURLService = resourceURLService;
    }

    private static final Logger log = LoggerFactory.getLogger(SavedMsgProcessor.class);

    @Override
    public SavedMsgData process(final SavedMsgData savedMsgData) throws Exception {
        String savedmessageid = savedMsgData.getSavedmessageid();
        String body = savedMsgData.getBody();
        String rawmessagedata = savedMsgData.getRawmessagedata();

        String rawMsgUrl = BatchUtil.extractRawMessageDataUrl(rawmessagedata);
        String updatedRawMsgUrl = resourceURLService.proccessUrl(rawMsgUrl);
        if (updatedRawMsgUrl != null) {
            rawmessagedata = rawmessagedata.replace(rawMsgUrl, updatedRawMsgUrl);
        }

        String bodyUrl = BatchUtil.extractUrl(body);
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


}