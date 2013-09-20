package test.dlmu.thread;

public class JoinTest extends Thread {
	static int result;

	public JoinTest(String name) {
		super(name);
	}

	@Override
	public void run() {
		System.out.println(this.getName() + "开始计算！");

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		Thread t = new JoinTest("计算线程");

		t.start();

		System.out.println("result:" + result);

		try {
			long start = System.nanoTime();
			t.join(2000);
			//t.join(2000);
			long end = System.nanoTime();
			System.out.println((end - start) / 1000000 + "毫秒后:" + result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
