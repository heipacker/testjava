package test.dlmu.collection.blockingQueue;

import java.util.concurrent.BlockingQueue;
/**
 * 消费者
 * @author Administrator
 *
 */
class Consumer implements Runnable {
	private BlockingQueue<String> drop;

	public Consumer(BlockingQueue<String> d) {
		this.drop = d;
	}

	public void run() {
		try {
			String msg = null;
			while (!((msg = drop.take()).equals("DONE"))){
				System.out.println(msg);
				try {
					Thread.sleep(1000*2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (InterruptedException intEx) {
			System.out.println("Interrupted! " + "Last one out, turn out the lights!");
		}
	}
}