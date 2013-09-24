package test.dlmu.lock;
/**
 * 自定义锁（非公平锁）
 * @author Administrator
 *
 */
public class Lock {
	private boolean isLocked = false;
	
	public synchronized void lock() throws InterruptedException{
		while(!isLocked){
			this.wait();
		}
		isLocked = true;
	}
	
	public synchronized void unlock(){
		isLocked = false;
		this.notify();
	}
}
