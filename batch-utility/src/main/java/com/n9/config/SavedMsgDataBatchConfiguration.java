package com.n9.config;

import com.n9.listener.ResourceCompletionNotificationListener;
import com.n9.model.SavedMsgData;
import com.n9.processor.SavedMsgProcessor;
import com.n9.service.ResourceURLService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


@Configuration
public class SavedMsgDataBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${savedMsgOutputFileDir}")
    private String outputFileDir;

    @Value("${savedMsgInputFilePath}")
    private String inputFilePath;

    @Autowired
    private ResourceURLService resourceURLService;


    public FlatFileItemReader<SavedMsgData> reader() {
        return new FlatFileItemReaderBuilder<SavedMsgData>()
                .name("savedMsgDataDataItemReader")
                .resource(new FileSystemResource(inputFilePath))
                .linesToSkip(1)
                .delimited()
//                .names(new String[]{"resourceid", "shorturl"})
                .names(new String[]{"savedmessageid", "organizationid", "agentid", "title", "body", "rawmessagedata", "createdatetime", "updatedatetime", "entid", "orgid"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<SavedMsgData>() {{
                    setTargetType(SavedMsgData.class);
                }})
                .build();
    }


    public SavedMsgProcessor processor() {
        return new SavedMsgProcessor(resourceURLService);
    }


    public FlatFileItemWriter<SavedMsgData> writer() {

        Resource outputResource = new FileSystemResource(outputFileDir + "/saved_msg_data.csv");

        //Create writer instance
        FlatFileItemWriter<SavedMsgData> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(outputResource);

        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(false);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<SavedMsgData>() {
                    {
                        setNames(new String[]{
                                "savedmessageid", "body","rawmessagedata"
                        });
                    }
                });
            }
        });
        return writer;


    }

    @Bean
    public Job savedMsgDataDataJob(ResourceCompletionNotificationListener listener) {
        return jobBuilderFactory.get("savedMsgDataDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }


    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<SavedMsgData, SavedMsgData>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}