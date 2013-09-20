package test.dlmu.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * 原子计数器
 * @author Administrator
 *
 */
public class AtomicCounter {
	private AtomicInteger value = new AtomicInteger();

	public int getValue() {
		return value.get();
	}

	public int increment() {
		return value.incrementAndGet();
	}

	public int increment(int i) {
		return value.addAndGet(i);
	}

	public int decrement() {
		return value.decrementAndGet();
	}

	public int decrement(int i) {
		return value.addAndGet(-i);
	}
}