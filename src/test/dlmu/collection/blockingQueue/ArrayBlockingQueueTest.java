package test.dlmu.collection.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * 生产者消费者测试
 * @author Administrator
 *
 */
public class ArrayBlockingQueueTest {
	public static void main(String[] args) {
		BlockingQueue<String> drop = new ArrayBlockingQueue<String>(1, true);
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