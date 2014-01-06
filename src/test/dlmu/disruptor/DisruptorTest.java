package test.dlmu.disruptor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.util.PaddedLong;
/**
 * 
 * @author Administrator
 *
 */
public class DisruptorTest {
	private static final Logger LOG = LoggerFactory.getLogger(DisruptorTest.class);
	//线程数
	private static final int THREAD_NUMS = 3;
	//Buffer大小
	private static final int BUFFER_SIZE = 1024 * 8;
	
	private static final long NUMS = 100000000L;

	public static void main(String[] args) throws InterruptedException {
		RingBuffer<MessageEvent> ringBuffer = RingBuffer.createSingleProducer(MessageEvent.EVENT_FACTORY, BUFFER_SIZE, new YieldingWaitStrategy());
		//使用线程池
		ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMS);
		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
		//消息事件处理器
		MessageMutationEventHandler[] handlers = new MessageMutationEventHandler[THREAD_NUMS];
		//消息事件批处理器 BatchEventProcessor从RingBuffer获取消息事件 然后调用MessageMutationEventHandler的onEvent方法处理消息事件
		BatchEventProcessor<?>[] batchEventProcessors = new BatchEventProcessor[THREAD_NUMS];

		for (int i = 0; i < THREAD_NUMS; i++) {
			handlers[i] = new MessageMutationEventHandler();
			batchEventProcessors[i] = new BatchEventProcessor<MessageEvent>(ringBuffer, sequenceBarrier, handlers[i]);
			ringBuffer.addGatingSequences(batchEventProcessors[i].getSequence());
		}
		
		CountDownLatch latch = new CountDownLatch(THREAD_NUMS);
		for (int i = 0; i < THREAD_NUMS; i++) {
			long n = batchEventProcessors[i].getSequence().get() + NUMS;
			LOG.info(n + "    " + NUMS + "  " + batchEventProcessors[i].getSequence().get());
			handlers[i].reset(latch, n);
			//提交批时间处理器
			executors.submit(batchEventProcessors[i]);
		}
		//计时开始
		long start = System.currentTimeMillis();

		for (long i = 0; i < NUMS; i++) {
			long sequence = ringBuffer.next();
			ringBuffer.get(sequence).setValue(i);
			//发布
			ringBuffer.publish(sequence);
		}
		//等待最后一个消费事件发生
		latch.await();
		long opsPerSecond = (NUMS * 1000L) / (System.currentTimeMillis() - start);

		for (int i = 0; i < THREAD_NUMS; i++) {
			batchEventProcessors[i].halt();
			if ((NUMS - 1) == handlers[i].getValue()) {
				LOG.info("-----" + handlers[i].getValue() + "-----");
			} else {
				LOG.error("error");
			}
		}
		executors.shutdown();
		LOG.info(String.format("Run %d, Disruptor=%,d ops/sec%n", 1, opsPerSecond));
	}
	/**
	 * 事件处理器
	 * @author Administrator
	 *
	 */
	public static final class MessageMutationEventHandler implements EventHandler<MessageEvent> {
		private final PaddedLong value = new PaddedLong();
		private long count;
		private CountDownLatch latch;

		public MessageMutationEventHandler() {

		}

		public long getValue() {
			return value.get();
		}

		public void reset(final CountDownLatch latch, final long expectedCount) {
			value.set(0L);
			this.latch = latch;
			count = expectedCount;
		}

		@Override
		public void onEvent(final MessageEvent event, final long sequence, final boolean endOfBatch) throws Exception {
			// log.info("onEvent:{}", event.getValue());
			value.set(event.getValue());
			LOG.info("onEvent:" + event.getValue());
			if (count == sequence) {
				//通知最后一个事件发生了
				latch.countDown();
			}
		}
	}
	/**
	 * 消息事件单元
	 * @author Administrator
	 *
	 */
	public static final class MessageEvent {
		private long value;

		public long getValue() {
			return value;
		}

		public void setValue(final long value) {
			this.value = value;
		}

		public final static EventFactory<MessageEvent> EVENT_FACTORY = new EventFactory<MessageEvent>() {
			public MessageEvent newInstance() {
				return new MessageEvent();
			}
		};
	}
}
