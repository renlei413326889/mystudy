package com.boleyn.netstudy.lession07;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class LongEventMain {
	public static void main(String[] args) throws InterruptedException {
		// Executor that will be used to construct new threads for consumers
		Executor executor = Executors.newCachedThreadPool();
		// The factory for the event
		LongEventFactory factory = new LongEventFactory();
		// Specify the size of the ring buffer, must be power of 2.
		int ringBufferSize = 1024 * 1024;
		
		/**
		 * 1、BlockingWaitStrategy是最低小的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致 的性能表现
		 * WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
		 * 2、SleepingWaitStrategy的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小
		 * WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
		 * 3、YieldingWaitStrategy的性能是最好的，适合用于低延迟的系统，在要求极高性能且事件处理线程数小于CPU逻辑核心数的场景中
		 * WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
		 */
		
		// Construct the Disruptor
		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
		// Connect the handler
		disruptor.handleEventsWith(new LongEventHandler());
		// Start the Disruptor, starts all threads running
		disruptor.start();
		// Get the ring buffer from the Disruptor to be used for publishing.
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

		LongEventProducer producer = new LongEventProducer(ringBuffer);

		ByteBuffer bb = ByteBuffer.allocate(8);
		for (long l = 0; true; l++) {
			bb.putLong(0, l);
			producer.onData(bb);
			Thread.sleep(1000);
		}
	}
}
