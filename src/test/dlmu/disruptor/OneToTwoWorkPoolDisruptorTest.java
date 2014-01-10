package test.dlmu.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;

/**
 * 
 * @author Administrator
 * 
 */
public class OneToTwoWorkPoolDisruptorTest {
	private static final Logger LOG = LoggerFactory.getLogger(DisruptorTest.class);
	// 线程数
	private static final int THREAD_NUMS = Runtime.getRuntime().availableProcessors();
	// Buffer大小
	private static final int BUFFER_SIZE = 1024 * 8;

	private static final long NUMS = 10000000L;

	public static void main(String[] args) throws InterruptedException {
		RingBuffer<MessageEvent> ringBuffer = RingBuffer.createSingleProducer(MessageEvent.EVENT_FACTORY, BUFFER_SIZE, new YieldingWaitStrategy());
		// 消息事件处理器
		@SuppressWarnings("unchecked")
		WorkHandler<MessageEvent>[] handlers = new WorkHandler[THREAD_NUMS];

		for (int i = 0; i < THREAD_NUMS; i++) {
			handlers[i] = new WorkHandler<MessageEvent>() {
				@Override
				public void onEvent(MessageEvent event) throws Exception {
					System.out.println(event.getValue());
					//LOG.info(Thread.currentThread().getName() + " " + event.getValue());
				}
			};
		}

		WorkerPool<MessageEvent> workerPool = new WorkerPool<MessageEvent>(ringBuffer, ringBuffer.newBarrier(), new IgnoreExceptionHandler(), handlers);
		ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
		//使用线程池
		ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMS);
		workerPool.start(executors);
		System.out.println("disruptor started ")
		;
		long start = System.currentTimeMillis();
		for (int i = 0; i < NUMS; i++) {
			long next = ringBuffer.next();
			try {
				ringBuffer.get(next).setValue(i);
			} finally {
				ringBuffer.publish(next);
			}
		}
		workerPool.drainAndHalt();
		Thread.sleep(1L);
		long opsPerSecond = (NUMS * 1000L) / (System.currentTimeMillis() - start);
		
		LOG.info(String.format("Run %d, Disruptor=%,d ops/sec", THREAD_NUMS, opsPerSecond));
		executors.shutdown();
	}

	/**
	 * 消息事件单元
	 * 
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
