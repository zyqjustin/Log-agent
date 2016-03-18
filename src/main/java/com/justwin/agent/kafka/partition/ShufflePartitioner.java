package com.justwin.agent.kafka.partition;

import java.util.Random;

import com.justwin.agent.kafka.AbstractPartitioner;

public class ShufflePartitioner extends AbstractPartitioner {
	
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
