package test.dlmu.collection.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockingQueue<String> drop = new SynchronousQueue<String>();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
        try {
			Thread.sleep(1000*30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
