package com.n9.config;

import com.n9.listener.ResourceCompletionNotificationListener;
import com.n9.model.TinyUrlData;
import com.n9.processor.TinyUrlProcessor;
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
public class ResourceBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${resourceOutputFileDir}")
    private String outputFileDir;

    @Value("${resourceInputFilePath}")
    private String inputFilePath;

    @Autowired
    private ResourceURLService resourceURLService;


    String[] inputCols = {"resourceid", "resourcecollectionid", "organizationid", "name", "description", "refkey",
            "fullurl", "shorturl", "createdatetime", "updatedatetime", "entid", "orgid"};
    String[] outputCols = {"resourceid", "shorturl"};


    public FlatFileItemReader<TinyUrlData> reader() {
        return new FlatFileItemReaderBuilder<TinyUrlData>()
                .name("tinyUrlDataItemReader")
                .resource(new FileSystemResource(inputFilePath))
                .linesToSkip(1)
                .delimited()
//                .names(new String[]{"resourceid", "shorturl"})
                .names(inputCols)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<TinyUrlData>() {{
                    setTargetType(TinyUrlData.class);
                }})
                .build();
    }


    public TinyUrlProcessor processor() {
        return new TinyUrlProcessor(resourceURLService);
    }


    public FlatFileItemWriter<TinyUrlData> writer() {

        Resource outputResource = new FileSystemResource(outputFileDir + "/resource_table.csv");

        //Create writer instance
        FlatFileItemWriter<TinyUrlData> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(outputResource);

        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(false);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<TinyUrlData>() {
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
                .<TinyUrlData, TinyUrlData>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

//    @Bean
    public Job importTinyUrlDataJob(ResourceCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importTinyUrlDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

}