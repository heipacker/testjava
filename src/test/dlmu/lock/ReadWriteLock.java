package test.dlmu.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义读写锁
 * 读写访问资源的条件概述：
 * 	读取：没有线程正在做写操作，且没有线程在请求写操作。
 * 	写入：没有线程正在做读写操作。
 * 参考：http://ifeve.com/read-write-locks/
 * @author Administrator
 * 
 */
public class ReadWriteLock {
	private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

	private int writeAccesses = 0;
	private int writeRequests = 0;
	private Thread writingThread = null;
	/**
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lockRead() throws InterruptedException {
		Thread callingThread = Thread.currentThread();
		while (!canGrantReadAccess(callingThread)) {
			wait();
		}

		readingThreads.put(callingThread, (getReadAccessCount(callingThread) + 1));
	}
	
	private boolean canGrantReadAccess(Thread callingThread) {
		if (isWriter(callingThread))//对应于写锁降到读锁（当已经占用写锁时请求读锁） 通过
			return true;
		if (hasWriter())//如果在请求读锁时有线程占用写锁 阻塞
			return false;
		if (isReader(callingThread))//如果在请求读锁时（可重入）已经占用读锁 通过
			return true;
		if (hasWriteRequests())//如果在请求读锁时有线程请求写锁 阻塞
			return false;
		return true;
	}
	
	public synchronized void unlockRead() {
		Thread callingThread = Thread.currentThread();
		if (!isReader(callingThread)) {
			throw new IllegalMonitorStateException("Calling Thread does not hold a read lock on this ReadWriteLock");
		}
		int accessCount = getReadAccessCount(callingThread);
		if (accessCount == 1) {//有必要删除吗？
			readingThreads.remove(callingThread);
		} else {
			readingThreads.put(callingThread, (accessCount - 1));
		}
		notifyAll();
	}
	/**
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lockWrite() throws InterruptedException {
		writeRequests++;
		Thread callingThread = Thread.currentThread();
		while (!canGrantWriteAccess(callingThread)) {
			wait();
		}
		writeRequests--;
		writeAccesses++;
		writingThread = callingThread;
	}
	/**
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void unlockWrite() throws InterruptedException {
		if (!isWriter(Thread.currentThread())) {
			throw new IllegalMonitorStateException("Calling Thread does not" + " hold the write lock on this ReadWriteLock");
		}
		writeAccesses--;
		if (writeAccesses == 0) {
			writingThread = null;
		}
		notifyAll();
	}

	private boolean canGrantWriteAccess(Thread callingThread) {
		if (isOnlyReader(callingThread))//只有一个读锁（升级写锁）时 请求写锁 通过
			return true;
		if (hasReaders())//当请求写锁时 已经有线程占有读锁 阻塞
			return false;
		if (writingThread == null)//第一次请求写锁 通过
			return true;
		if (!isWriter(callingThread))//当请求写锁时当前线程不占用写锁 阻塞
			return false;
		return true;
	}

	private int getReadAccessCount(Thread callingThread) {
		Integer accessCount = readingThreads.get(callingThread);
		if (accessCount == null)
			return 0;
		return accessCount.intValue();
	}

	private boolean hasReaders() {
		return readingThreads.size() > 0;
	}

	private boolean isReader(Thread callingThread) {
		return readingThreads.get(callingThread) != null;
	}

	private boolean isOnlyReader(Thread callingThread) {
		return readingThreads.size() == 1 && readingThreads.get(callingThread) != null;
	}

	private boolean hasWriter() {
		return writingThread != null;
	}

	private boolean isWriter(Thread callingThread) {
		return writingThread == callingThread;
	}

	private boolean hasWriteRequests() {
		return this.writeRequests > 0;
	}
}