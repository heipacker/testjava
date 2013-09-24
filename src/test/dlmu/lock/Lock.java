package test.dlmu.lock;
/**
 * 自定义锁（非公平锁）可重入
 * @author Administrator
 *
 */
public class Lock {
	//防止notify在wait方法之前执行
	private boolean isLocked = false;
	
	private Thread currentThread = null;
	
	private int lockCount = 0;
	
	public synchronized void lock() throws InterruptedException{
		Thread cThread = Thread.currentThread();
		boolean flag = this.currentThread!=cThread;
		while(isLocked && flag){//while自旋锁 防止线程被虚假唤醒
			this.wait();
		}
		isLocked = true;
		++lockCount;
		if(flag){//防止多次赋相同值
			this.currentThread = cThread;
		}
	}
	
	public synchronized void unlock(){
		if(Thread.currentThread()!=this.currentThread){
			throw new IllegalMonitorStateException("current thread did not lock this Object!");
		}
		--lockCount;
		if(lockCount==0){
			isLocked = false;
			this.notify();
		}
	}
}
