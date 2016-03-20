package com.justwin.agent.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;

public abstract class AbstractPartitioner {

	public abstract int partition(ProducerRecord<byte[], byte[]> record, Cluster cluster);

}
 