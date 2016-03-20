package com.justwin.agent.kafka.partition;

import java.util.List;
import java.util.Random;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import com.justwin.agent.kafka.AbstractPartitioner;

/**
 * just for test api
 * @author justwin
 * @date 2016年3月20日
 * @version 1.0
 * @description {@link Partitioner.class}
 */
public class ShufflePartitioner extends AbstractPartitioner {
	
	private Random random;
	
	public ShufflePartitioner() {
		super();
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public int partition(ProducerRecord<byte[], byte[]> record, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.partitionsForTopic(record.topic());
        int numPartitions = partitions.size();		
        return random.nextInt(10000) % numPartitions;
	}

}
