package test.dlmu.semaphore;
/**
 * 自定义信号量
 * @author Administrator
 *
 */
public class Semaphore {
	private boolean signal = false;
	/**
	 * 相当于notify()方法
	 */
	public synchronized void take(){
		this.signal = true;
		this.notify();
	}
	/**
	 * 相当于wait()方法
	 * @throws InterruptedException
	 */
	public synchronized void release() throws InterruptedException{
		while(!this.signal){
			this.wait();
		}
		this.signal = false;
	}
}
