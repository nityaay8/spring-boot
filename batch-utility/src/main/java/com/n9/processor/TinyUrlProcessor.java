package com.n9.processor;

import com.n9.model.TinyUrlData;
import com.n9.service.ResourceURLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class TinyUrlProcessor implements ItemProcessor<TinyUrlData, TinyUrlData> {

    private ResourceURLService resourceURLService;

    public TinyUrlProcessor(ResourceURLService resourceURLService) {
        this.resourceURLService = resourceURLService;
    }

    private static final Logger log = LoggerFactory.getLogger(TinyUrlProcessor.class);

    @Override
    public TinyUrlData process(final TinyUrlData tinyUrlData) throws Exception {
        final String resourceid = tinyUrlData.getResourceid().toUpperCase();
        final String shorturl = resourceURLService.proccessUrl(tinyUrlData.getShorturl());

        final TinyUrlData transformedTinyUrlData = new TinyUrlData(resourceid, shorturl);

        log.info("Converting (" + tinyUrlData + ") into (" + transformedTinyUrlData + ")");

        return transformedTinyUrlData;
    }

}