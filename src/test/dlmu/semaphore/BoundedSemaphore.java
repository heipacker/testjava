package test.dlmu.semaphore;
/**
 * 有上限的信号量
 * @author Administrator
 *
 */
public class BoundedSemaphore {
	private int signal = 0;
	
	private int bound;
	
	public BoundedSemaphore(int bound){
		this.bound = bound;
	}
	/**
	 * 相当于notify()方法
	 * @throws InterruptedException 
	 */
	public synchronized void take() throws InterruptedException{
		while(this.signal == this.bound){
			this.wait();
		}
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
		this.notify();
	}
}
