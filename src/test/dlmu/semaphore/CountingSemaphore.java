package test.dlmu.semaphore;
/**
 * 可计数信号量
 * @author Administrator
 *
 */
public class CountingSemaphore {
	private int signal = 0;
	/**
	 * 相当于notify()方法
	 */
	public synchronized void take(){
		++this.signal;
		this.notify();
	}
	/**
	 * 相当于wait()方法
	 * @throws InterruptedException
	 */
	public synchronized void release() throws InterruptedException{
		while(this.signal == 0){
			this.wait();
		}
		--this.signal;
	}
}
