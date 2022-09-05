package com.n9.processor;

import com.n9.model.QuestionsInfo;
import com.n9.service.ResourceURLService;
import com.n9.util.BatchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class QuestionsProcessor implements ItemProcessor<QuestionsInfo, QuestionsInfo> {

    private static final Logger log = LoggerFactory.getLogger(QuestionsProcessor.class);

    private ResourceURLService resourceURLService;


    public QuestionsProcessor(ResourceURLService resourceURLService) {


        this.resourceURLService = resourceURLService;
    }

    @Override
    public QuestionsInfo process(QuestionsInfo questionsInfo) throws Exception {

        String questionid = questionsInfo.getQuestionid();

        String summary = questionsInfo.getSummary();

        String summaryUrl = BatchUtil.extractUrl(summary);
        String updatedSummaryUrl = resourceURLService.proccessUrl(summaryUrl);
        if (updatedSummaryUrl != null) {
            summary = summary.replace(summaryUrl, updatedSummaryUrl);
        }

        String body = questionsInfo.getBody();

        String bodyUrl = BatchUtil.extractUrl(body);
        String updatedBodyUrl = resourceURLService.proccessUrl(bodyUrl);
        if (updatedBodyUrl != null) {
            body = body.replace(bodyUrl, updatedBodyUrl);
        }
        QuestionsInfo transformedQuestionsInfo = new QuestionsInfo();
        transformedQuestionsInfo.setBody(body);
        transformedQuestionsInfo.setSummary(summary);
        transformedQuestionsInfo.setQuestionid(questionid);

        log.info("Converting (" + questionsInfo + ") into (" + transformedQuestionsInfo + ")");

        return transformedQuestionsInfo;
    }
}
