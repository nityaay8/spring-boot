package com.n9.config;

import com.n9.listener.ResourceCompletionNotificationListener;
import com.n9.model.QuestionsInfo;
import com.n9.processor.QuestionsProcessor;
import com.n9.service.ResourceURLService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
@EnableBatchProcessing
public class QuestionsBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${questionsOutputFileDir}")
    private String outputFileDir;

    @Value("${questionsInputFilePath}")
    private String inputFilePath;


    String[] inputCols = {"questionid", "qtopicid", "summary", "body", "refkey", "createdatetime", "updatedatetime", "entid", "orgid"};
    String[] outputCols = {"questionid", "summary", "body"};

    @Autowired
    private ResourceURLService resourceURLService;


    public FlatFileItemReader<QuestionsInfo> reader() {
        return new FlatFileItemReaderBuilder<QuestionsInfo>()
                .name("questionsDataItemReader")
                .resource(new FileSystemResource(inputFilePath))
                .linesToSkip(1)
                .delimited()
                .names(inputCols)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<QuestionsInfo>() {{
                    setTargetType(QuestionsInfo.class);
                }})
                .build();
    }


    public QuestionsProcessor processor() {
        return new QuestionsProcessor(resourceURLService);
    }


    public FlatFileItemWriter<QuestionsInfo> writer() {

        Resource outputResource = new FileSystemResource(outputFileDir + "/questions.csv");

        //Create writer instance
        FlatFileItemWriter<QuestionsInfo> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(outputResource);

        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(false);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<QuestionsInfo>() {
                    {
                        setNames(outputCols);
                    }
                });
            }
        });
        return writer;


    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<QuestionsInfo, QuestionsInfo>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job questionsInfoDataJob(ResourceCompletionNotificationListener listener) {
        return jobBuilderFactory.get("questionsInfoDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

}