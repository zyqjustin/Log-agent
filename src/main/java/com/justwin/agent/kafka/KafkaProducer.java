package com.justwin.agent.kafka;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.log4j.Logger;

import com.justwin.agent.conf.Setting;
import com.justwin.agent.kafka.partition.ShufflePartitioner;
import com.justwin.agent.util.AgentConstants;

public class KafkaProducer implements Runnable {
	private static final Logger _logger = Logger.getLogger(KafkaProducer.class);

	private String topic;
	private Properties conf;

	private long lastTimeFileSize;
	private RandomAccessFile readFile;
	private AbstractPartitioner partitioner;
	private final Producer<String, String> producer;
	
	public KafkaProducer(Setting setting, long lastTimeFileSize, RandomAccessFile readFile) {
		this.conf = init(setting);
		this.topic = (String) setting.get(AgentConstants.KAFKA_TOPIC);
		this.readFile = readFile;
		this.lastTimeFileSize = lastTimeFileSize;
		this.partitioner = new ShufflePartitioner();
		this.producer = new Producer<String, String>(new ProducerConfig(conf));
	}

	private Properties init(final Setting setting) {
		String kafkaBrokerList = (String) setting.get(AgentConstants.KAFKA_BROKER_LIST);
		String kafkaKeySerializerClazz = (String) setting.getWithDefault(
				AgentConstants.KAFKA_KEY_SERIALIZER_CLASS,
				AgentConstants.DEFAULT_KAFKA_SERIALIZER_CLASS);
		String kafkaSerializerClazz = (String) setting.getWithDefault(
				AgentConstants.KAFKA_SERIALIZER_CLASS,
				AgentConstants.DEFAULT_KAFKA_SERIALIZER_CLASS);
		String kafkaRequestRequiredAcks = (String) setting.getWithDefault(
				AgentConstants.KAFKA_REQUEST_REQUIRED_ACKS,
				AgentConstants.DEFAULT_REQUEST_REQUIRED_ACKS);

		Properties properties = new Properties();
		properties.put("metadata.broker.list", kafkaBrokerList);
		properties.put("serializer.class", kafkaSerializerClazz);
		properties.put("key.serializer.class", kafkaKeySerializerClazz);
		properties.put("request.required.acks", kafkaRequestRequiredAcks);
		
		return properties;
	}

	@Override
	public void run() {
		try {
			readFile.seek(lastTimeFileSize);
			String content = "";     
            while( (content = readFile.readLine()) != null) {
                producer.send(new KeyedMessage<String, String>(topic, "", content));
            }
            lastTimeFileSize = readFile.length();
		} catch (IOException e) {
			_logger.error("Report file failed, last offset: " + lastTimeFileSize, e);
		}
	}
	
}
