package test.dlmu.thread.atomic;

/**
 * 测试原子计数器
 * 
 * @author Administrator
 * 
 */
public class AtomicCounterTest extends Thread {

	AtomicCounter counter = new AtomicCounter();

	public AtomicCounterTest(AtomicCounter counter) {
		this.counter = counter;
	}

	private static final int THREAD_LENGTH = 5;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AtomicCounter counter = new AtomicCounter();

		Thread[] thread = new Thread[THREAD_LENGTH];
		for (int i = 0; i < thread.length; ++i) {
			thread[i] = new AtomicCounterTest(counter);
			thread[i].start();
		}

		for (int i = 0; i < thread.length; ++i) {
			try {
				thread[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		System.out.println("counter index:" + counter.increment());
	}
}
