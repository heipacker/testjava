package test.dlmu.collection.blockingQueue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
/**
 * 生产者
 * @author Administrator
 *
 */
class Producer implements Runnable {
	private BlockingQueue<String> drop;
	List<String> messages = Arrays.asList("Mares eat oats", "Does eat oats", "Little lambs eat ivy", "Wouldn't you eat ivy too?");

	public Producer(BlockingQueue<String> d) {
		this.drop = d;
	}

	public void run() {
		try {
			for (String s : messages){
				drop.put(s);
				try {
					Thread.sleep(1000*1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			drop.put("DONE");
		} catch (InterruptedException intEx) {
			System.out.println("Interrupted! " + "Last one out, turn out the lights!");
		}
	}
}