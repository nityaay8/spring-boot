package com.demo.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.demo.model.MsgInfo;

@Service
public class ProducerService {
	Logger log = LoggerFactory.getLogger(ProducerService.class);

	public static final String TOPIC_NAME = "topic_0";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public MsgInfo send(String msg) throws Exception {
		String key = UUID.randomUUID().toString();
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC_NAME, key, msg);
		CompletableFuture<SendResult<String, String>> resultFuture = kafkaTemplate.send(producerRecord);
		SendResult sendResult = resultFuture.get();
		log.info("sendResult = " + sendResult);

		MsgInfo msgInfo = new MsgInfo();
		msgInfo.setStatus("success");
		msgInfo.setId(sendResult.getProducerRecord().key().toString());
		msgInfo.setTime(sendResult.getRecordMetadata().timestamp());

		return msgInfo;
	}
}
