package test.dlmu.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 测试闭锁
 * @author Administrator
 *
 */
public class TestCountDownLatch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			long time = timeTasks(10, new Thread() {
				@Override
				public void run() {
					long g = 0;
					for (int i = 0; i < 1000; ++i) {
						g += i;
					}
					System.out.println(g);
				}
			});

			System.out.println("totalTime:" + time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static long timeTasks(int nThreads, final Runnable task)
			throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);

		for (int i = 0; i < nThreads; ++i) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException ignored) {

					}
				}
			};
			t.start();
		}

		long start = System.nanoTime();
		// 触发所有线程开始执行
		startGate.countDown();
		// 等待所有线程执行结束
		endGate.await();
		return System.nanoTime() - start;
	}
}
