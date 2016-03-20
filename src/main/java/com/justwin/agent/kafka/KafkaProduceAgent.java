package com.justwin.agent.kafka;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import com.justwin.agent.conf.Setting;
import com.justwin.agent.kafka.partition.ShufflePartitioner;
import com.justwin.agent.util.AgentConstants;

/**
 * 
 * @author justwin
 * @date 2016年3月20日
 * @version 1.0
 * @description TODO consider add compression
 */
public class KafkaProduceAgent implements Runnable {
	private static final Logger _logger = Logger
			.getLogger(KafkaProduceAgent.class);

	private String topic;
	private Properties conf;

	private long lastTimeFileSize;
	private RandomAccessFile readFile;
	private AbstractPartitioner partitioner; // TODO add more partition strategy
	private final KafkaProducer<String, String> producer;

	public KafkaProduceAgent(Setting setting, long lastTimeFileSize,
			RandomAccessFile readFile) {
		this.conf = init(setting);
		this.topic = (String) setting.get(AgentConstants.KAFKA_TOPIC);
		this.readFile = readFile;
		this.lastTimeFileSize = lastTimeFileSize;
		this.partitioner = new ShufflePartitioner();
		this.producer = new KafkaProducer<String, String>(conf);
	}

	private Properties init(final Setting setting) {
		String kafkaBrokerList = (String) setting
				.get(AgentConstants.KAFKA_BROKER_LIST);
		String kafkaKeySerializerClazz = (String) setting.getWithDefault(
				AgentConstants.KAFKA_KEY_SERIALIZER_CLASS,
				AgentConstants.DEFAULT_KAFKA_SERIALIZER_CLASS);
		String kafkaValSerializerClazz = (String) setting.getWithDefault(
				AgentConstants.KAFKA_VALUE_SERIALIZER_CLASS,
				AgentConstants.DEFAULT_KAFKA_SERIALIZER_CLASS);
		String kafkaRequestRequiredAcks = (String) setting.getWithDefault(
				AgentConstants.KAFKA_REQUEST_REQUIRED_ACKS,
				AgentConstants.DEFAULT_REQUEST_REQUIRED_ACKS);

		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerList);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaKeySerializerClazz);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaValSerializerClazz);
		properties.put(ProducerConfig.ACKS_CONFIG, kafkaRequestRequiredAcks);

		return properties;
	}

	@Override
	public void run() {
		try {
			readFile.seek(lastTimeFileSize);
			String content = "";
			while ((content = readFile.readLine()) != null) {
				producer.send(new ProducerRecord<String, String>(topic, content));
			}
			lastTimeFileSize = readFile.length();
		} catch (IOException e) {
			_logger.error("Report file failed, last offset: " + lastTimeFileSize, e);
		}
	}

}
