package com.justwin.agent.kafka;

import java.util.Random;

import kafka.producer.Partitioner;

public class ShufflePartitioner implements Partitioner {
	
	private Random random;
	
	public ShufflePartitioner() {
		super();
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public int partition(Object obj, int numPartitions) {
		int partitionNum = 0;
		if (obj instanceof String) {
			partitionNum = random.nextInt(10000) % numPartitions;
		}
		return partitionNum;
	}

}
