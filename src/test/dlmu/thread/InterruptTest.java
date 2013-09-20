package test.dlmu.thread;

public class InterruptTest extends Thread {
	static int result;

	public InterruptTest(String name) {
		super(name);
	}

	@Override
	public void run() {
		System.out.println(this.getName() + "开始计算！");

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(this.getName() + "被中断！");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		result = (int) (Math.random() * 10000);

		System.out.println(this.getName() + "计算结束！");
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("主线程执行！");

		Thread t = new InterruptTest("计算线程");

		t.start();

		System.out.println("result:" + result);

		try {
			long start = System.nanoTime();
			t.join(2000);
			long end = System.nanoTime();
			System.out.println("1:" + t.getName() + "是否中断：" + t.isInterrupted());
			t.interrupt();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("2:" + t.getName() + "是否中断：" + t.isInterrupted());
			System.out.println((end - start) / 1000000 + "毫秒后:" + result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
