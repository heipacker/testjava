package test.dlmu.thread;

import java.util.concurrent.TimeUnit;

public class TestInterrupt1 {
	public static void main(String[] args) {
		Thread t = new MyThread();
		t.start();
		t.interrupt();
		System.out.println("已调用线程的interrupt方法");
	}

	static class MyThread extends Thread {
		public void run() {
			int num = 0;
			try {
				num = longTimeRunningNonInterruptMethod(2, 0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("线程被中断！");
				return;
			}
			System.out.println("长时间任务运行结束,num=" + num);
			System.out.println("线程的中断状态:" + Thread.interrupted());
		}

		private static int longTimeRunningNonInterruptMethod(int count,
				int initNum) throws InterruptedException {
			for (int i = 0; i < count; i++) {
				TimeUnit.NANOSECONDS.sleep(10);
			}
			return initNum;
		}
	}
}