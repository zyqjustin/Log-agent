package com.justwin.agent.kafka;

import kafka.producer.Partitioner;

public abstract class AbstractPartitioner implements Partitioner {

	@Override
	public abstract int partition(Object obj, int numPartitions);

}
 